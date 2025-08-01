package com.pesupal.server.service.implementations.module;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModulePermission;
import com.pesupal.server.model.module.ModuleRole;
import com.pesupal.server.repository.ModulePermissionRepository;
import com.pesupal.server.service.interfaces.module.ModulePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModulePermissionServiceImpl implements ModulePermissionService {

    private final ModulePermissionRepository modulePermissionRepository;

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
}
