package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModulePermission;
import com.pesupal.server.model.module.ModuleRole;

public interface ModulePermissionService {

    ModulePermission getModulePermissionByModuleAndRole(Module module, ModuleRole moduleRole);

    void initializeModulePermissions(Module module);

    void deleteAllPermissionsInModule(String moduleId);
}
