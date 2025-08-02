package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.SortColumnDto;
import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.PaginatedData;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.MandatoryDataMissingException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.factory.RecordRelationFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.ModuleHelper;
import com.pesupal.server.model.module.*;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.PublicIdProjection;
import com.pesupal.server.repository.ModuleRecordRepository;
import com.pesupal.server.service.interfaces.module.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModuleRecordServiceImpl extends CurrentValueRetriever implements ModuleRecordService {

    private final ModuleService moduleService;
    private final ModuleFieldService moduleFieldService;
    private final ModuleMemberService moduleMemberService;
    private final RecordRelationFactory recordRelationFactory;
    private final ModuleRecordRepository moduleRecordRepository;
    private final ModulePermissionService modulePermissionService;
    private final ModuleRecordTimelineService moduleRecordTimelineService;

    /**
     * Creates a new record in a module.
     *
     * @param createModuleRecordDto
     * @return
     */
    @Override
    @Transactional
    public ModuleRecordDto createRecord(CreateModuleRecordDto createModuleRecordDto) {

        OrgMember orgMember = getCurrentOrgMember();

        String moduleId = createModuleRecordDto.getModuleId();
        Module module = moduleService.getModuleById(moduleId);

        ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);
        ModuleRole moduleRole = moduleMember.getRole();

        // 1. Check if the current user has permission to create a record in the module
        ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleRole);
        if (!modulePermission.isCreateRecord()) {
            throw new PermissionDeniedException("You do not have permission to create a record in this module.");
        }

        // 2. If they have permission, Check whether the module has been published
        if (!module.isActive()) {
            throw new ActionProhibitedException("You cannot create a record in an inactive module. Publish the module first.");
        }

        Map<String, Object> data = createModuleRecordDto.getData();
        if (data == null) {
            throw new MandatoryDataMissingException("The 'data' field is required but not provided.");
        }
        String subject = (String) data.get("subject");

        // 3. Validate that the subject is provided and not empty
        if (subject == null || subject.isEmpty()) {
            throw new MandatoryDataMissingException("The 'subject' field is required but not provided.");
        }

        // 4. Check if the subject is unique if duplicates are not allowed
//        if (!module.isAllowDuplicateSubject()  && moduleRecordRepository.existsByModuleAndSubject(module, subject)) {
//            throw new DuplicateDataReceivedException("Record with subject '" + subject + "' already exists in this module.");
//        }

        // 5. Create the module record with basic information
        ModuleRecord moduleRecord = new ModuleRecord();
        moduleRecord.setModule(module);
        moduleRecordRepository.save(moduleRecord);

        // 6. Retrieve all module fields for the module
        List<ModuleField> moduleFields = moduleFieldService.getModuleFieldsByModuleId(moduleId);
        for (ModuleField moduleField : moduleFields) {

            String attribute = ModuleHelper.getAttributeName(moduleField);
            Object value = data.get(attribute);
            FieldClassification fieldClassification = moduleField.getClassification();

            // 7. If the field is SYSTEM_FIELD, get the system value if applicable
            if (fieldClassification.equals(FieldClassification.SYSTEM_FIELD)) {

                Optional<Object> systemValue = moduleFieldService.getSystemValueIfApplicable(moduleRecord, moduleField, value);
                if (systemValue.isPresent()) {
                    value = systemValue.get();
                }
            }

            // 8. If the value is null, check if the field is required
            if (value == null) {
                if (moduleField.isRequired()) {
                    throw new MandatoryDataMissingException("The field '" + moduleField.getName() + "' is required but not provided.");
                }
                continue; // Skip optional fields that are not provided
            }

            FieldType fieldType = moduleField.getFieldType();

            // 9. Get the appropriate RecordRelationService based on the field type to save the value
            RecordRelationService recordRelationService = recordRelationFactory.getRelationService(fieldType);
            recordRelationService.save(moduleRecord, moduleField, value);
        }

        // 10. Log the creation of the record in the timeline
        moduleRecordTimelineService.createTimeLine(ModuleRecordTimeline.builder().record(moduleRecord).actionPerformedBy(orgMember).action("Created a new record.").build());

        return ModuleRecordDto.fromModuleRecord(moduleRecord);
    }

    /**
     * Deletes a record by its public ID.
     *
     * @param recordId
     */
    @Override
    @Transactional
    public void deleteRecord(String recordId) {

        OrgMember orgMember = getCurrentOrgMember();

        ModuleRecord moduleRecord = getModuleRecordByPublicId(recordId);
        Module module = moduleRecord.getModule();
        String moduleId = module.getPublicId();

        ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);

        // 1. Check if the module member is active
        if (!moduleMember.isActive()) {
            throw new PermissionDeniedException("You are no longer part of this module.");
        }

        ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleMember.getRole());

        // 2. Check if the current user has permission to delete a record in the module
        if (!ModuleHelper.isModuleOwner(module, orgMember) && !modulePermission.isDeleteRecord()) {
            throw new PermissionDeniedException("You do not have permission to delete this record.");
        }

        // 3. Retrieve all module fields for the module
        List<ModuleField> moduleFields = moduleFieldService.getModuleFieldsByModuleId(moduleId);
        for (ModuleField moduleField : moduleFields) {

            FieldType fieldType = moduleField.getFieldType();

            // 4. Get the appropriate RecordRelationService based on the field type to delete the value
            RecordRelationService recordRelationService = recordRelationFactory.getRelationService(fieldType);
            recordRelationService.delete(moduleRecord, moduleField);
        }

        // 5. Delete timeline entries related to this record
        moduleRecordTimelineService.deleteByModuleRecord(moduleRecord);

        // 6. Finally, delete the module record
        moduleRecordRepository.delete(moduleRecord);
    }

    /**
     * Retrieves a module record by its public ID.
     *
     * @param recordId
     * @return
     */
    public ModuleRecord getModuleRecordByPublicId(String recordId) {

        return moduleRecordRepository.findByPublicId(recordId).orElseThrow(() -> new DataNotFoundException("Record with ID " + recordId + " not found."));
    }

    /**
     * Retrieves a record by its ID.
     *
     * @param recordId
     * @return
     * @view <b>Detail view</b> of a record
     */
    @Override
    public ModuleRecordDto getRecordById(String recordId, ModuleView moduleView) {

        OrgMember orgMember = getCurrentOrgMember();

        ModuleRecord moduleRecord = getModuleRecordByPublicId(recordId);
        Module module = moduleRecord.getModule();
        String moduleId = module.getPublicId();

        ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);
        ModuleRole moduleRole = moduleMember.getRole();
        ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleRole);

        if (!modulePermission.isReadRecord()) {
            throw new PermissionDeniedException("You do not have permission to view this record.");
        }

        ModuleRecordDto moduleRecordDto = ModuleRecordDto.fromModuleRecord(moduleRecord);
        List<ModuleFieldDto> fields = moduleRecordDto.getFields();

        List<ModuleField> moduleFields = moduleFieldService.getModuleFieldsByModuleId(moduleId);
        for (ModuleField moduleField : moduleFields) {

            FieldType fieldType = moduleField.getFieldType();

            if (moduleField.getRestrictFrom().contains(moduleRole)) {
                continue;   // Skip fields that are restricted for the current role
            }

            if (!moduleView.canShowField(moduleField)) {
                continue;   // Skip fields that are not meant to be shown in detail view
            }

            RecordRelationService recordRelationService = recordRelationFactory.getRelationService(fieldType);
            ModuleFieldDto moduleFieldDto = recordRelationService.getByModuleRecordAndModuleField(moduleRecord, moduleField);
            fields.add(moduleFieldDto);
        }
        return moduleRecordDto;
    }

    /**
     * Retrieves all records with pagination.
     *
     * @param page
     * @param size
     * @return
     * @view <b>List view</b> of records
     */
    @Override
    public PaginatedData<List<ModuleRecordDto>> getAllRecords(String moduleId, int page, int size, SortColumnDto sortColumnDto) {

        Sort sort = null;
        if (sortColumnDto == null) {
            sort = Sort.by("createdAt").descending();
        } else {
            sort = sortColumnDto.getOrder().isAscending() ? Sort.by(sortColumnDto.getColumn()).ascending() : Sort.by(sortColumnDto.getColumn()).descending();
        }

        Pageable pageable = PageRequest.of(page, size + 1, sort);
//        Pageable pageable = Pageable.ofSize(size + 1).withPage(page);

        Module module = moduleService.getModuleById(moduleId);

        int totalRecords = moduleRecordRepository.countAllByModule(module);

        List<String> recordsIds = totalRecords > 0 ? new ArrayList<>(moduleRecordRepository.findAllPublicIdByModule(module, pageable).getContent().stream().map(PublicIdProjection::getPublicId).toList()) : new ArrayList<>();

        boolean hasMoreRecords = recordsIds.size() == size + 1;
        Map<String, Object> info = Map.of(
                "hasMoreRecords", hasMoreRecords,
                "page", page,
                "size", size,
                "totalRecords", totalRecords
        );

        if (!recordsIds.isEmpty() && recordsIds.size() > size) {
            recordsIds.remove(recordsIds.size() - 1); // safely remove the last element
        }

        List<ModuleRecordDto> moduleRecordDtos = new ArrayList<>();
        for (String recordId : recordsIds) {
            moduleRecordDtos.add(getRecordById(recordId, ModuleView.LIST_VIEW));
        }
        return new PaginatedData<>(moduleRecordDtos, info);
    }

    /**
     * Deletes all records associated with a module.
     *
     * @param moduleId
     */
    @Override
    @Transactional
    public void deleteAllRecords(String moduleId) {

        OrgMember orgMember = getCurrentOrgMember();

        Module module = moduleService.getModuleById(moduleId);


        if (!ModuleHelper.isModuleOwner(module, orgMember)) {

            ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);
            ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleMember.getRole());
            if (!modulePermission.isClearRecords()) {
                throw new PermissionDeniedException("You do not have permission to clear records of this module.");
            }
        }

        List<ModuleField> moduleFields = moduleFieldService.getModuleFieldsByModuleId(moduleId);
        for (ModuleField moduleField : moduleFields) {

            FieldType fieldType = moduleField.getFieldType();

            RecordRelationService recordRelationService = recordRelationFactory.getRelationService(fieldType);
            recordRelationService.deleteAllByModule(module);
        }

        moduleRecordTimelineService.deleteAllByModule(module);

        moduleRecordRepository.deleteAllByModule(module);
    }
}
