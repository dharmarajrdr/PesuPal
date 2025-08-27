package com.pesupal.server.service.interfaces.module.relation;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordUserRelation;
import com.pesupal.server.service.interfaces.module.RecordRelationService;

public interface RecordUserRelationService extends RecordRelationService {
    RecordUserRelation getRecordSelectRelation(ModuleRecord record, ModuleField field);
}
