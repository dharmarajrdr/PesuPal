package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordStringRelation;
import com.pesupal.server.repository.RecordStringRelationRepository;
import com.pesupal.server.service.interfaces.module.relation.RecordStringRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecordStringRelationServiceImpl implements RecordStringRelationService {

    private final RecordStringRelationRepository recordStringRelationRepository;

    /**
     * Saves the relation data for a module record and field.
     *
     * @param record
     * @param field
     * @param data
     */
    @Override
    public void save(ModuleRecord record, ModuleField field, Object data) {

        String value = (String) data;

        RecordStringRelation recordStringRelation = new RecordStringRelation();
        recordStringRelation.setRecord(record);
        recordStringRelation.setField(field);
        recordStringRelation.setValue(value);
        recordStringRelationRepository.save(recordStringRelation);
    }

    /**
     * Retrieves the relation data for a module record and field.
     *
     * @param moduleRecord
     * @param moduleField
     * @return
     */
    @Override
    public ModuleFieldDto getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField) {

        return recordStringRelationRepository.findByRecordAndField(moduleRecord, moduleField).orElseThrow(() -> new DataNotFoundException("No data found."));
    }

    /**
     * Deletes the relation data for a module record and field.
     *
     * @param moduleRecord
     * @param moduleField
     */
    @Override
    public void delete(ModuleRecord moduleRecord, ModuleField moduleField) {

        recordStringRelationRepository.deleteAllByRecordAndField(moduleRecord, moduleField);
    }
}
