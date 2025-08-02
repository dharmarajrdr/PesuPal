package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.request.module.AddModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;

import java.util.List;
import java.util.Optional;

public interface ModuleFieldService {

    ModuleFieldDto addModuleField(AddModuleFieldDto addModuleFieldDto);
    
    Optional<Object> getSystemValueIfApplicable(ModuleRecord moduleRecord, ModuleField moduleField, Object value);

    List<ModuleFieldDto> getModuleFields(String moduleId);

    void deleteModuleField(Long fieldId);

    List<ModuleField> getModuleFieldsByModuleId(String moduleId);

    void deleteAllFields(String moduleId);

    void addSystemFieldsIntoModule(Module module);
}
