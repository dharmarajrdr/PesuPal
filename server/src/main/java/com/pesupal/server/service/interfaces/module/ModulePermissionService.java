package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModulePermission;
import com.pesupal.server.model.module.ModuleRole;
import com.pesupal.server.model.user.OrgMember;

public interface ModulePermissionService {

    ModulePermission getModulePermissionByModuleAndRole(Module module, ModuleRole moduleRole);

    void initializeModulePermissions(Module module);

    void deleteAllPermissionsInModule(String moduleId);

    ModulePermission verifyModuleAccessibility(Module module, OrgMember orgMember);
}
