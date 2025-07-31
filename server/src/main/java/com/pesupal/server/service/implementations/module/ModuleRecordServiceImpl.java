package com.pesupal.server.service.implementations.module;

import com.pesupal.server.dto.request.module.CreateModuleRecordDto;
import com.pesupal.server.dto.response.module.ModuleRecordDto;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.module.ModulePermission;
import com.pesupal.server.model.module.ModuleRole;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModuleRecordRepository;
import com.pesupal.server.service.interfaces.module.ModuleMemberService;
import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import com.pesupal.server.service.interfaces.module.ModuleRecordService;
import com.pesupal.server.service.interfaces.module.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleRecordServiceImpl extends CurrentValueRetriever implements ModuleRecordService {

    private final ModuleRecordRepository moduleRecordRepository;
    private final ModuleService moduleService;
    private final ModulePermissionService modulePermissionService;
    private final ModuleMemberService moduleMemberService;

    /**
     * Creates a new record in a module.
     *
     * @param createModuleRecordDto
     * @return
     */
    @Override
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
        
        return null;
    }
}
