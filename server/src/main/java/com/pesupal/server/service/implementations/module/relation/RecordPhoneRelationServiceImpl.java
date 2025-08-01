package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.enums.CountryCode;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.MandatoryDataMissingException;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordPhoneRelation;
import com.pesupal.server.repository.RecordPhoneRelationRepository;
import com.pesupal.server.service.interfaces.module.relation.RecordPhoneRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class RecordPhoneRelationServiceImpl implements RecordPhoneRelationService {

    private final RecordPhoneRelationRepository recordPhoneRelationRepository;

    /**
     * Validates the phone number format and country code.
     *
     * @param number
     */
    private void validatePhoneNumber(String number, CountryCode countryCode) {

        // 1. check for non-numeric characters
        if (!number.matches("\\d+")) {
            throw new IllegalArgumentException("Phone number must contain only digits.");
        }

        // 2. Check the digits of the phone number against the country code
        if (number.length() != countryCode.getDigits()) {
            throw new IllegalArgumentException("Phone number must be " + countryCode.getDigits() + " digits long for country code " + countryCode);
        }
    }

    /**
     * Saves the phone relation data for a given record and field.
     *
     * @param record
     * @param field
     * @param data
     */
    @Override
    public void save(ModuleRecord record, ModuleField field, Object data) {

        if (!(data instanceof Map)) {
            throw new IllegalArgumentException("The field '" + field.getName() + "' must contain details of 'country_code' and 'number'.");
        }

        Map<String, Object> phoneData = (Map<String, Object>) data;
        CountryCode countryCode = CountryCode.valueOf((String) phoneData.get("country_code"));
        String number = (String) phoneData.get("number");
        if (number == null || number.isBlank()) {
            if (field.isRequired()) {
                throw new MandatoryDataMissingException("The field " + field.getName() + " is mandatory and cannot be empty.");
            }
            return; // If the field is not required, we can skip saving an empty phone number.
        }

        number = number.replace(" ", "");

        validatePhoneNumber(number, countryCode);

        RecordPhoneRelation recordPhoneRelation = new RecordPhoneRelation();
        recordPhoneRelation.setRecord(record);
        recordPhoneRelation.setField(field);
        recordPhoneRelation.setNumber(number);
        recordPhoneRelation.setCountryCode(countryCode);
        recordPhoneRelationRepository.save(recordPhoneRelation);
    }

    /**
     * Retrieves the phone relation data for a given record and field.
     *
     * @param moduleRecord
     * @param moduleField
     * @return
     */
    @Override
    public ModuleFieldDto<Map<String, Object>> getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField) {

        RecordPhoneRelation recordPhoneRelation = recordPhoneRelationRepository.findByRecordAndField(moduleRecord, moduleField).orElseThrow(() -> new DataNotFoundException("No phone relation found for the given record and field."));
        Map<String, Object> data = Map.of(
                "country_code", recordPhoneRelation.getCountryCode().name(),
                "number", recordPhoneRelation.getNumber()
        );
        return ModuleFieldDto.fromModuleFieldWithData(moduleField, data);
    }

    /**
     * Deletes the phone relation data for a given record and field.
     *
     * @param moduleRecord
     * @param moduleField
     */
    @Override
    public void delete(ModuleRecord moduleRecord, ModuleField moduleField) {

        recordPhoneRelationRepository.deleteAllByRecordAndField(moduleRecord, moduleField);
    }

    /**
     * Deletes all phone relations associated with a specific module.
     *
     * @param module
     */
    @Override
    public void deleteAllByModule(Module module) {

        recordPhoneRelationRepository.deleteAllByRecord_Module(module);
    }
}
