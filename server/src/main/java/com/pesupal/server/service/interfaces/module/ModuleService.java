package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.module.CreateModuleDto;
import com.pesupal.server.dto.response.module.ModulePreviewDto;
import com.pesupal.server.model.module.Module;

import java.util.List;

public interface ModuleService {

    Module createModule(CreateModuleDto createModuleDto);

    Module getModuleById(String moduleId);

    void publishModule(String moduleId);

    List<ModulePreviewDto> getAllModulesPreview();
}
