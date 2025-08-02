package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordDateTimeRelation;
import com.pesupal.server.repository.RecordDateTimeRelationRepository;
import com.pesupal.server.service.interfaces.module.relation.RecordDateTimeRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RecordDateTimeRelationServiceImpl implements RecordDateTimeRelationService {

    private final RecordDateTimeRelationRepository recordDateTimeRelationRepository;

    /**
     * Saves the date-time relation data for a given record and field.
     *
     * @param record
     * @param field
     * @param data
     */
    @Override
    public void save(ModuleRecord record, ModuleField field, Object data) {

        LocalDateTime dateTime = (LocalDateTime) data;
        RecordDateTimeRelation recordDateTimeRelation = new RecordDateTimeRelation();
        recordDateTimeRelation.setRecord(record);
        recordDateTimeRelation.setField(field);
        recordDateTimeRelation.setDateTime(dateTime);
        recordDateTimeRelationRepository.save(recordDateTimeRelation);
    }

    /**
     * Retrieves the date-time relation data for a given module record and module field.
     *
     * @param moduleRecord
     * @param moduleField
     * @return
     */
    @Override
    public ModuleFieldDto<LocalDateTime> getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField) {

        ModuleFieldDto<LocalDateTime> moduleFieldDto = ModuleFieldDto.fromModuleField(moduleField);
        RecordDateTimeRelation recordDateTimeRelation = recordDateTimeRelationRepository.findByRecordAndField(moduleRecord, moduleField).orElseThrow(() -> new DataNotFoundException("No date-time relation found for the given record and field."));
        moduleFieldDto.setData(recordDateTimeRelation.getDateTime());
        return moduleFieldDto;
    }

    /**
     * Deletes the date-time relation for a given module record and module field.
     *
     * @param moduleRecord
     * @param moduleField
     */
    @Override
    public void delete(ModuleRecord moduleRecord, ModuleField moduleField) {

        recordDateTimeRelationRepository.deleteByRecordAndField(moduleRecord, moduleField);
    }

    /**
     * Deletes all date-time relations for a given module.
     *
     * @param module
     */
    @Override
    public void deleteAllByModule(Module module) {

        recordDateTimeRelationRepository.deleteAllByRecord_Module(module);
    }
}
