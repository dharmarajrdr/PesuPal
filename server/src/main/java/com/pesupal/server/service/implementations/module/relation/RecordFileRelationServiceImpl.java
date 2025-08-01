package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordFileRelation;
import com.pesupal.server.repository.RecordFileRelationRepository;
import com.pesupal.server.service.interfaces.module.relation.RecordFileRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RecordFileRelationServiceImpl implements RecordFileRelationService {

    private final RecordFileRelationRepository recordFileRelationRepository;

    /**
     * Saves the data for a given record and field.
     *
     * @param record
     * @param field
     * @param data
     */
    @Override
    public void save(ModuleRecord record, ModuleField field, Object data) {

        if (!(data instanceof Map)) {
            throw new IllegalArgumentException("The field '" + field.getName() + "' must contain details of 'mediaId' and 'extension'.");
        }

        Map<String, Object> fileData = (Map<String, Object>) data;
        UUID mediaId = (UUID) fileData.get("mediaId");
        String extension = (String) fileData.get("extension");
        if (field.isRequired()) {
            if (mediaId == null) {
                throw new IllegalArgumentException("The field '" + field.getName() + "' is required and must contain a valid media ID.");
            }
            if (extension == null || extension.isBlank()) {
                throw new IllegalArgumentException("The field '" + field.getName() + "' is required and must contain a valid file extension.");
            }
        }
        if (mediaId == null || mediaId.toString().isBlank()) {
            throw new IllegalArgumentException("The field '" + field.getName() + "' must contain a valid media ID.");
        }
        if (extension == null || extension.isBlank()) {
            throw new IllegalArgumentException("The field '" + field.getName() + "' must contain a valid file extension.");
        }

        RecordFileRelation recordFileRelation = new RecordFileRelation();
        recordFileRelation.setRecord(record);
        recordFileRelation.setField(field);
        recordFileRelation.setMediaId(mediaId);
        recordFileRelation.setExtension(extension);
        recordFileRelationRepository.save(recordFileRelation);
    }

    /**
     * Retrieves the data for a given record and field.
     *
     * @param moduleRecord
     * @param moduleField
     * @return
     */
    @Override
    public ModuleFieldDto getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField) {

        RecordFileRelation recordFileRelation = recordFileRelationRepository.findByRecordAndField(moduleRecord, moduleField).orElseThrow(() -> new IllegalArgumentException("No file relation found for record: " + moduleRecord.getId() + " and field: " + moduleField.getId()));

        Map<String, Object> data = Map.of(
                "mediaId", recordFileRelation.getMediaId(),
                "extension", recordFileRelation.getExtension()
        );

        return ModuleFieldDto.fromModuleFieldWithData(moduleField, data);
    }

    /**
     * Deletes the record for a given module record and field.
     *
     * @param moduleRecord
     * @param moduleField
     */
    @Override
    public void delete(ModuleRecord moduleRecord, ModuleField moduleField) {

        recordFileRelationRepository.deleteByRecordAndField(moduleRecord, moduleField);
    }

    /**
     * Deletes all records related to a specific module.
     *
     * @param module
     */
    @Override
    public void deleteAllByModule(Module module) {

        recordFileRelationRepository.deleteAllByRecord_Module(module);
    }
}
