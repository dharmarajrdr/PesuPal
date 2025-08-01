package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.exceptions.MandatoryDataMissingException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.factory.RecordRelationFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
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

        if (!module.isActive()) {
            throw new ActionProhibitedException("You cannot create a record in an inactive module. Publish the module first.");
        }

        ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);
        ModuleRole moduleRole = moduleMember.getRole();

        // 1. Check if the current user has permission to create a record in the module
        ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleRole);
        if (!modulePermission.isCreateRecord()) {
            throw new PermissionDeniedException("You do not have permission to create a record in this module.");
        }

        Map<String, Object> data = createModuleRecordDto.getData();
        if (data == null) {
            throw new MandatoryDataMissingException("The 'data' field is required but not provided.");
        }
        String subject = (String) data.get("subject");

        // 2. Validate that the subject is provided and not empty
        if (subject == null || subject.isEmpty()) {
            throw new MandatoryDataMissingException("The 'subject' field is required but not provided.");
        }

        // 3. Check if the subject is unique if duplicates are not allowed
        if (!module.isAllowDuplicateSubject() && moduleRecordRepository.existsByModuleAndSubject(module, subject)) {
            throw new DuplicateDataReceivedException("Record with subject '" + subject + "' already exists in this module.");
        }

        // 4. Create the module record with basic information
        ModuleRecord moduleRecord = new ModuleRecord();
        moduleRecord.setModule(module);
        moduleRecord.setCreatedBy(orgMember);
        moduleRecord.setSubject(subject);
        moduleRecordRepository.save(moduleRecord);

        // 5. Retrieve all module fields for the module
        List<ModuleField> moduleFields = moduleFieldService.getModuleFieldsByModuleId(moduleId);
        for (ModuleField moduleField : moduleFields) {

            String attribute = moduleField.getName().replace(" ", "_").toLowerCase();
            Object value = data.get(attribute);

            // 6. If the value is null, check if the field is required
            if (value == null) {
                if (moduleField.isRequired()) {
                    throw new MandatoryDataMissingException("The field '" + moduleField.getName() + "' is required but not provided.");
                }
                continue; // Skip optional fields that are not provided
            }

            FieldType fieldType = moduleField.getFieldType();

            // 7. Get the appropriate RecordRelationService based on the field type to save the value
            RecordRelationService recordRelationService = recordRelationFactory.getRelationService(fieldType);
            recordRelationService.save(moduleRecord, moduleField, value);
        }

        // 8. Log the creation of the record in the timeline
        moduleRecordTimelineService.createTimeLine(ModuleRecordTimeline.builder().record(moduleRecord).actionPerformedBy(orgMember).action("Created a new record.").build());

        return ModuleRecordDto.fromModuleRecord(moduleRecord);
    }
}
