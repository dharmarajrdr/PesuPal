package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordStringRelation;
import com.pesupal.server.repository.RecordStringRelationRepository;
import com.pesupal.server.service.interfaces.module.relation.RecordStringRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public ModuleFieldDto<String> getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField) {

        Optional<RecordStringRelation> recordStringRelationOptional = recordStringRelationRepository.findByRecordAndField(moduleRecord, moduleField);
        String value = recordStringRelationOptional.isPresent() ? recordStringRelationOptional.get().getValue() : null;
        return ModuleFieldDto.fromModuleFieldWithData(moduleField, value);
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

    /**
     * Deletes all relation data associated with a specific module.
     *
     * @param module
     */
    @Override
    public void deleteAllByModule(Module module) {

        recordStringRelationRepository.deleteAllByRecord_Module(module);
    }
}
