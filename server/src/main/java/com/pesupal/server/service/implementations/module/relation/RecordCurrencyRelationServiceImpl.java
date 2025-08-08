package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.enums.Currency;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.relation.RecordCurrencyRelation;
import com.pesupal.server.repository.module.relation.RecordCurrencyRelationRepository;
import com.pesupal.server.service.implementations.module.RecordRelationServiceImpl;
import com.pesupal.server.service.interfaces.module.relation.RecordCurrencyRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecordCurrencyRelationServiceImpl extends RecordRelationServiceImpl implements RecordCurrencyRelationService {

    private final RecordCurrencyRelationRepository recordCurrencyRelationRepository;

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
            throw new IllegalArgumentException("The field '" + field.getName() + "' must contain details of 'currency' and 'amount'.");
        }

        Map<String, Object> currencyData = (Map<String, Object>) data;
        String currencyCode = (String) currencyData.get("currency");
        Double amount = (Double) currencyData.get("amount");

        if (field.isRequired()) {
            if (currencyCode == null) {
                throw new IllegalArgumentException("The field '" + field.getName() + "' is required and must contain a valid currency.");
            }
            if (amount == null || amount < 0) {
                throw new IllegalArgumentException("The field '" + field.getName() + "' is required and must contain a valid amount.");
            }
            return; // If the field is not required, we simply return without saving anything
        }

        RecordCurrencyRelation recordCurrencyRelation = new RecordCurrencyRelation();
        recordCurrencyRelation.setRecord(record);
        recordCurrencyRelation.setField(field);
        recordCurrencyRelation.setCurrency(Currency.valueOf(currencyCode));
        recordCurrencyRelation.setAmount(amount);
        recordCurrencyRelationRepository.save(recordCurrencyRelation);
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

        Optional<RecordCurrencyRelation> optionalRecordCurrencyRelation = recordCurrencyRelationRepository.findByRecordAndField(moduleRecord, moduleField);
        ModuleFieldDto moduleFieldDto = ModuleFieldDto.fromModuleField(moduleField);
        if (optionalRecordCurrencyRelation.isEmpty()) {
            if (moduleField.isRequired()) {
                throw new DataNotFoundException("The field '" + moduleField.getName() + "' is required but no data found for record: " + moduleRecord.getId());
            }
        } else {
            RecordCurrencyRelation recordCurrencyRelation = optionalRecordCurrencyRelation.get();
            Map<String, Object> currencyData = Map.of(
                    "currency", recordCurrencyRelation.getCurrency().name(),
                    "amount", recordCurrencyRelation.getAmount()
            );
            moduleFieldDto.setData(currencyData);
        }
        return moduleFieldDto;
    }

    /**
     * Deletes the record currency relation for a given module record and module field.
     *
     * @param moduleRecord
     * @param moduleField
     */
    @Override
    public void delete(ModuleRecord moduleRecord, ModuleField moduleField) {

        recordCurrencyRelationRepository.deleteAllByRecordAndField(moduleRecord, moduleField);
    }

    /**
     * Deletes all record currency relations associated with a specific module.
     *
     * @param module
     */
    @Override
    public void deleteAllByModule(Module module) {

        recordCurrencyRelationRepository.deleteAllByRecord_Module(module);
    }
}
