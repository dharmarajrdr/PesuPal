package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.exceptions.*;
import com.pesupal.server.factory.RecordRelationFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.ModuleHelper;
import com.pesupal.server.model.module.*;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleRecordRepository;
import com.pesupal.server.service.interfaces.module.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
        if (!module.isAllowDuplicateSubject() && moduleRecordRepository.existsByModuleAndSubject(module, subject)) {
            throw new DuplicateDataReceivedException("Record with subject '" + subject + "' already exists in this module.");
        }

        // 5. Create the module record with basic information
        ModuleRecord moduleRecord = new ModuleRecord();
        moduleRecord.setModule(module);
        moduleRecord.setCreatedBy(orgMember);
        moduleRecord.setSubject(subject);
        moduleRecordRepository.save(moduleRecord);

        // 6. Retrieve all module fields for the module
        List<ModuleField> moduleFields = moduleFieldService.getModuleFieldsByModuleId(moduleId);
        for (ModuleField moduleField : moduleFields) {

            String attribute = ModuleHelper.getAttributeName(moduleField);
            Object value = data.get(attribute);

            // 7. If the value is null, check if the field is required
            if (value == null) {
                if (moduleField.isRequired()) {
                    throw new MandatoryDataMissingException("The field '" + moduleField.getName() + "' is required but not provided.");
                }
                continue; // Skip optional fields that are not provided
            }

            FieldType fieldType = moduleField.getFieldType();

            // 8. Get the appropriate RecordRelationService based on the field type to save the value
            RecordRelationService recordRelationService = recordRelationFactory.getRelationService(fieldType);
            recordRelationService.save(moduleRecord, moduleField, value);
        }

        // 9. Log the creation of the record in the timeline
        moduleRecordTimelineService.createTimeLine(ModuleRecordTimeline.builder().record(moduleRecord).actionPerformedBy(orgMember).action("Created a new record.").build());

        return ModuleRecordDto.fromModuleRecord(moduleRecord);
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
    public ModuleRecordDto getRecordById(String recordId) {

        OrgMember orgMember = getCurrentOrgMember();

        ModuleRecord moduleRecord = getModuleRecordByPublicId(recordId);
        Module module = moduleRecord.getModule();
        String moduleId = module.getPublicId();

        ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);
        ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleMember.getRole());

        if (!modulePermission.isReadRecord()) {
            throw new PermissionDeniedException("You do not have permission to view this record.");
        }

        ModuleRecordDto moduleRecordDto = ModuleRecordDto.fromModuleRecord(moduleRecord);

        List<ModuleField> moduleFields = moduleFieldService.getModuleFieldsByModuleId(moduleId);
        for (ModuleField moduleField : moduleFields) {

            String attribute = ModuleHelper.getAttributeName(moduleField);
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
    public List<ModuleRecordDto> getAllRecords(Integer page, Integer size) {
        return List.of();
    }
}
