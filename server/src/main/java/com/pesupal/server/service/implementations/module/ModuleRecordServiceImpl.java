package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.enums.FieldType;
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
    private final ModulePermissionService modulePermissionService;
    private final ModuleRecordTimelineService moduleRecordTimelineService;
    private final ModuleRecordService moduleRecordService;
    private final ModuleRecordRepository moduleRecordRepository;

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

        ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleRole);
        if (!modulePermission.isCreateRecord()) {
            throw new PermissionDeniedException("You do not have permission to create a record in this module.");
        }

        Map<String, Object> data = createModuleRecordDto.getData();

        String subject = (String) data.get("subject");
        if (subject == null || subject.isEmpty()) {
            throw new MandatoryDataMissingException("The 'subject' field is required but not provided.");
        }

        if (!module.isAllowDuplicateSubject() && moduleRecordRepository.existsByModuleAndSubject(module, subject)) {
            throw new DuplicateDataReceivedException("Record with subject '" + subject + "' already exists in this module.");
        }

        ModuleRecord moduleRecord = new ModuleRecord();
        moduleRecord.setCreatedBy(orgMember);
        moduleRecord.setSubject(subject);

        List<ModuleField> moduleFields = moduleFieldService.getModuleFieldsByModuleId(moduleId);
        for (ModuleField moduleField : moduleFields) {
            String attribute = moduleField.getName().replace(" ", "_").toLowerCase();
            Object value = data.get(attribute);
            if (value == null) {
                if (moduleField.isRequired()) {
                    throw new MandatoryDataMissingException("The field '" + moduleField.getName() + "' is required but not provided.");
                }
                continue; // Skip optional fields that are not provided
            }
            FieldType fieldType = moduleField.getFieldType();
            RecordRelationService recordRelationService = recordRelationFactory.getRelationService(fieldType);
            recordRelationService.save(value);
        }

        moduleRecordTimelineService.createTimeLine(ModuleRecordTimeline.builder().record(moduleRecord).actionPerformedBy(orgMember).action("Created a new record.").build());

        return null;
    }
}
