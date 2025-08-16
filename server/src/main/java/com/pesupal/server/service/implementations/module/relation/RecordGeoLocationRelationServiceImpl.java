package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.request.LocationDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordGeoLocationRelation;
import com.pesupal.server.repository.module.relation.RecordGeoLocationRelationRepository;
import com.pesupal.server.service.implementations.module.RecordRelationServiceImpl;
import com.pesupal.server.service.interfaces.module.relation.RecordGeoLocationRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RecordGeoLocationRelationServiceImpl extends RecordRelationServiceImpl implements RecordGeoLocationRelationService {

    private final RecordGeoLocationRelationRepository recordGeoLocationRelationRepository;

    /**
     * Saves the data for a given record and field.
     *
     * @param record
     * @param field
     * @param data
     */
    @Override
    public void save(ModuleRecord record, ModuleField field, Object data) {

        LocationDto locationData = (LocationDto) data;

        if (field.isRequired()) {
            if (locationData == null || locationData.getLatitude() == null || locationData.getLongitude() == null) {
                throw new IllegalArgumentException("The field '" + field.getName() + "' is required and must contain valid latitude and longitude.");
            }
            return; // If the field is not required, we simply return without saving anything
        }

        RecordGeoLocationRelation recordGeoLocationRelation = new RecordGeoLocationRelation();
        recordGeoLocationRelation.setRecord(record);
        recordGeoLocationRelation.setField(field);
        recordGeoLocationRelation.setLatitude(locationData.getLatitude());
        recordGeoLocationRelation.setLongitude(locationData.getLongitude());
        recordGeoLocationRelationRepository.save(recordGeoLocationRelation);

    }

    /**
     * Retrieves the module field data for a given module record and field.
     *
     * @param moduleRecord
     * @param moduleField
     * @return
     */
    @Override
    public ModuleFieldDto getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField) {

        Optional<RecordGeoLocationRelation> optionalRecordGeoLocationRelation = recordGeoLocationRelationRepository.findByRecordAndField(moduleRecord, moduleField);
        ModuleFieldDto moduleFieldDto = ModuleFieldDto.fromModuleField(moduleField);
        if (optionalRecordGeoLocationRelation.isEmpty()) {
            if (moduleField.isRequired()) {
                throw new IllegalArgumentException("The field '" + moduleField.getName() + "' is required but no data found.");
            }
        } else {
            RecordGeoLocationRelation recordGeoLocationRelation = optionalRecordGeoLocationRelation.get();
            LocationDto locationDto = new LocationDto(recordGeoLocationRelation.getLatitude(), recordGeoLocationRelation.getLongitude());
            moduleFieldDto.setData(locationDto);
        }
        return moduleFieldDto;
    }

    /**
     * Deletes the record geolocation relation for a given module record and field.
     *
     * @param moduleRecord
     * @param moduleField
     */
    @Override
    @Transactional
    public void delete(ModuleRecord moduleRecord, ModuleField moduleField) {

        recordGeoLocationRelationRepository.deleteAllByRecordAndField(moduleRecord, moduleField);
    }

    /**
     * Deletes all record geolocation relations for a given module.
     *
     * @param module
     */
    @Override
    @Transactional
    public void deleteAllByModule(Module module) {

        recordGeoLocationRelationRepository.deleteAllByRecord_Module(module);
    }
}
