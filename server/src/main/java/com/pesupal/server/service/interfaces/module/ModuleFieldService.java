package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.module.AddModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.model.module.ModuleField;

import java.util.List;

public interface ModuleFieldService {

    ModuleFieldDto addModuleField(AddModuleFieldDto addModuleFieldDto);

    List<ModuleFieldDto> getModuleFields(String moduleId);

    void deleteModuleField(Long fieldId);

    List<ModuleField> getModuleFieldsByModuleId(String moduleId);
}
