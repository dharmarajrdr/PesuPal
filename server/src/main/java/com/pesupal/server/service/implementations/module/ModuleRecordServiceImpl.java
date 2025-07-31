package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.module.*;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.service.interfaces.module.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ModuleRecordServiceImpl extends CurrentValueRetriever implements ModuleRecordService {

    private final ModuleService moduleService;
    private final ModuleMemberService moduleMemberService;
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

        ModulePermission modulePermission = modulePermissionService.getModulePermissionByModuleAndRole(module, moduleRole);
        if (!modulePermission.isCreateRecord()) {
            throw new PermissionDeniedException("You do not have permission to create a record in this module.");
        }

        ModuleRecord moduleRecord = new ModuleRecord();
        moduleRecord.setCreatedBy(orgMember);

        moduleRecordTimelineService.createTimeLine(ModuleRecordTimeline.builder().record(moduleRecord).actionPerformedBy(orgMember).action("Created a new record.").build());

        return null;
    }
}
