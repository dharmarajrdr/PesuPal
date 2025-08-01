package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleSelectOptionDto;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.ModuleSelectOption;
import com.pesupal.server.model.module.relation.RecordSelectRelation;
import com.pesupal.server.repository.RecordSelectRelationRepository;
import com.pesupal.server.service.interfaces.module.ModuleSelectOptionService;
import com.pesupal.server.service.interfaces.module.relation.RecordSelectRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecordSelectRelationServiceImpl implements RecordSelectRelationService {

    private final ModuleSelectOptionService moduleSelectOptionService;
    private final RecordSelectRelationRepository recordSelectRelationRepository;

    /**
     * Saves the relation data for a module record and field.
     *
     * @param record
     * @param field
     * @param data
     */
    @Override
    public void save(ModuleRecord record, ModuleField field, Object data) {

        RecordSelectRelation recordSelectRelation = new RecordSelectRelation();
        recordSelectRelation.setRecord(record);
        recordSelectRelation.setField(field);
        Long selectOptionId = ((Number) data).longValue();
        if (selectOptionId != null) {
            ModuleSelectOption moduleSelectOption = moduleSelectOptionService.getModuleSelectOptionByModuleFieldAndId(field, selectOptionId);
            recordSelectRelation.setSelectOption(moduleSelectOption);
        }
        recordSelectRelationRepository.save(recordSelectRelation);
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

        ModuleFieldDto<List<ModuleSelectOptionDto>> moduleFieldDto = ModuleFieldDto.fromModuleField(moduleField);

        // 1. Retrieve all selected options for the given field
        List<RecordSelectRelation> recordSelectRelations = recordSelectRelationRepository.findAllByRecordAndField(moduleRecord, moduleField);
        List<Long> selectedOptionIds = recordSelectRelations.stream().map(relation -> relation.getSelectOption().getId()).toList();

        // 2. Select all options for the given field
        List<ModuleSelectOption> moduleSelectOptions = moduleSelectOptionService.getAllByModuleField(moduleField);
        List<ModuleSelectOptionDto> moduleSelectOptionDtos = moduleSelectOptions.stream().map(moduleSelectOption -> ModuleSelectOptionDto.fromModuleSelectOption(moduleSelectOption, selectedOptionIds.contains(moduleSelectOption.getId()))).toList();
        moduleFieldDto.setData(moduleSelectOptionDtos);

        return moduleFieldDto;
    }
}
