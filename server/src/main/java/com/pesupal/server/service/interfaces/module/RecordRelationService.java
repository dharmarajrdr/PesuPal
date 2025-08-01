package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;

public interface RecordRelationService {

    void save(ModuleRecord record, ModuleField field, Object data);

    Object getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField);
}
