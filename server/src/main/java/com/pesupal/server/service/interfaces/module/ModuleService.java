package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.model.module.Module;

public interface ModuleService {

    Module createModule(CreateModuleDto createModuleDto);

    Module getModuleById(String moduleId);
}
