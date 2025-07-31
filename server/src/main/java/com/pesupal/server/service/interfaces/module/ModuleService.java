package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;

public interface ModuleService {

    boolean isModuleOwner(Module module, OrgMember orgMember);

    Module createModule(CreateModuleDto createModuleDto);

    Module getModuleById(String moduleId);
}
