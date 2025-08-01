package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordLinkRelation;
import com.pesupal.server.repository.RecordLinkRelationRepository;
import com.pesupal.server.service.interfaces.module.relation.RecordLinkRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class RecordLinkRelationServiceImpl implements RecordLinkRelationService {

    private final RecordLinkRelationRepository recordLinkRelationRepository;

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
            throw new IllegalArgumentException("The field '" + field.getName() + "' must contain details of 'title' and 'url'.");
        }

        Map<String, Object> linkData = (Map<String, Object>) data;
        String title = (String) linkData.get("title");
        String url = (String) linkData.get("url");
        if (field.isRequired()) {
            if (url == null || url.isBlank()) {
                throw new IllegalArgumentException("The field '" + field.getName() + "' is required and must contain a valid URL.");
            }
            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("The field '" + field.getName() + "' is required and must contain a valid title.");
            }
            return; // If the field is not required, we simply return without saving anything
        }

        RecordLinkRelation recordLinkRelation = new RecordLinkRelation();
        recordLinkRelation.setRecord(record);
        recordLinkRelation.setField(field);
        recordLinkRelation.setTitle(title);
        recordLinkRelation.setUrl(url);
        recordLinkRelationRepository.save(recordLinkRelation);
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

        RecordLinkRelation recordLinkRelation = recordLinkRelationRepository.findByRecordAndField(moduleRecord, moduleField).orElseThrow(() -> new DataNotFoundException("No link relation found for record: " + moduleRecord.getId() + " and field: " + moduleField.getId()));
        Map<String, Object> data = Map.of(
                "title", recordLinkRelation.getTitle(),
                "url", recordLinkRelation.getUrl()
        );
        return ModuleFieldDto.fromModuleFieldWithData(moduleField, data);
    }

    /**
     * Deletes the data for a given record and field.
     *
     * @param moduleRecord
     * @param moduleField
     */
    @Override
    public void delete(ModuleRecord moduleRecord, ModuleField moduleField) {

        recordLinkRelationRepository.deleteAllByRecordAndField(moduleRecord, moduleField);
    }

    /**
     * Deletes all data associated with a specific module.
     *
     * @param module
     */
    @Override
    public void deleteAllByModule(Module module) {

        recordLinkRelationRepository.deleteAllByRecord_Module(module);
    }
}
