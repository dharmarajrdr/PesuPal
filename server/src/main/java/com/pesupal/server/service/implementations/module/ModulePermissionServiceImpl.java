package com.pesupal.server.service.implementations.module;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.module.*;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.ModulePermissionRepository;
import com.pesupal.server.service.interfaces.module.ModuleMemberService;
import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ModulePermissionServiceImpl extends CurrentValueRetriever implements ModulePermissionService {

    private final ModuleMemberService moduleMemberService;
    private final ModulePermissionRepository modulePermissionRepository;

    public ModulePermissionServiceImpl(@Lazy ModuleMemberService moduleMemberService, ModulePermissionRepository modulePermissionRepository) {
        this.moduleMemberService = moduleMemberService;
        this.modulePermissionRepository = modulePermissionRepository;
    }

    /**
     * Retrieves a module permission by module ID and role.
     *
     * @param module
     * @param moduleRole
     * @return
     */
    @Override
    public ModulePermission getModulePermissionByModuleAndRole(Module module, ModuleRole moduleRole) {

        return modulePermissionRepository.findByModuleAndRole(module, moduleRole).orElseThrow(() -> new DataNotFoundException("Permission configuration missing for role '" + moduleRole + "' in this module."));
    }

    /**
     * Initializes the permissions for a module based on predefined roles.
     *
     * @param module
     */
    @Override
    public void initializeModulePermissions(Module module) {

        ModulePermission ownerModulePermission = ModulePermission.builder().module(module).role(ModuleRole.OWNER).createRecord(true).readRecord(true).manageMembers(true).deleteRecord(true).build();
        modulePermissionRepository.save(ownerModulePermission);

        ModulePermission maintainerModulePermission = ModulePermission.builder().module(module).role(ModuleRole.MAINTAINER).createRecord(true).readRecord(true).manageMembers(false).deleteRecord(false).build();
        modulePermissionRepository.save(maintainerModulePermission);

        ModulePermission memberModulePermission = ModulePermission.builder().module(module).role(ModuleRole.MEMBER).createRecord(false).readRecord(true).manageMembers(false).deleteRecord(false).build();
        modulePermissionRepository.save(memberModulePermission);
    }

    /**
     * Deletes all permissions associated with a module.
     *
     * @param moduleId
     */
    @Override
    public void deleteAllPermissionsInModule(String moduleId) {

        modulePermissionRepository.deleteAllByModule_PublicId(moduleId);
    }

    /**
     * Verifies the accessibility of a module based on the current user's organization membership and the module's accessibility settings.
     *
     * @param module
     * @return
     */
    @Override
    public ModulePermission verifyModuleAccessibility(Module module) {

        ModuleAccessibility moduleAccessibility = module.getAccessibility();
        OrgMember orgMember = getCurrentOrgMember();
        switch (moduleAccessibility) {
            case ONLY_ME: {
                if (!module.getCreatedBy().getPublicId().equals(orgMember.getPublicId())) {
                    throw new PermissionDeniedException("You do not have permission to access this module.");
                }
            }
            case ANYONE_IN_ORG: {
                if (!module.getCreatedBy().getOrg().getId().equals(orgMember.getOrg().getId())) {
                    throw new PermissionDeniedException("Module does not exist in your organization.");
                }
            }
            case SELECTIVE_MEMBERS: {
                ModuleMember moduleMember = moduleMemberService.getModuleMemberByOrgMemberAndModule(orgMember, module);
                return getModulePermissionByModuleAndRole(module, moduleMember.getRole());
            }
            default: {
                throw new PermissionDeniedException("Module accessibility is not defined.");
            }
        }
    }
}
