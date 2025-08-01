package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;

public interface RecordRelationService {

    void save(ModuleRecord record, ModuleField field, Object data);

    ModuleFieldDto getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField);

    void delete(ModuleRecord moduleRecord, ModuleField moduleField);

    void deleteAllByModule(Module module);
}
