package com.pesupal.server.service.implementations.module.relation;

import com.pesupal.server.dto.request.module.AddModuleFieldDto;
import com.pesupal.server.dto.response.module.ModuleFieldDto;
import com.pesupal.server.dto.response.module.TransitionDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRecord;
import com.pesupal.server.model.module.Transition;
import com.pesupal.server.model.module.relation.RecordTransitionRelation;
import com.pesupal.server.repository.RecordTransitionRelationRepository;
import com.pesupal.server.service.interfaces.module.TransitionService;
import com.pesupal.server.service.interfaces.module.relation.RecordTransitionRelationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecordTransitionRelationServiceImpl implements RecordTransitionRelationService {

    private final TransitionService transitionService;
    private final RecordTransitionRelationRepository recordTransitionRelationRepository;

    /**
     * Saves a record transition relation.
     *
     * @param record
     * @param field
     * @param data
     */
    @Override
    public void save(ModuleRecord record, ModuleField field, Object data) {

        Optional<RecordTransitionRelation> optionalRecordTransitionRelation = recordTransitionRelationRepository.findByRecordAndField(record, field);
        RecordTransitionRelation recordTransitionRelation;
        // If the record transition relation does not exist, create a new one
        if (optionalRecordTransitionRelation.isEmpty()) {
            recordTransitionRelation = new RecordTransitionRelation();
            recordTransitionRelation.setRecord(record);
            recordTransitionRelation.setField(field);
        } else {    // If it exists, use the existing one
            recordTransitionRelation = optionalRecordTransitionRelation.get();
        }
        Long transitionId = ((Number) data).longValue();
        Transition transition = transitionService.getByIdAndField(transitionId, field);
        recordTransitionRelation.setTransition(transition);
        recordTransitionRelationRepository.save(recordTransitionRelation);
    }

    /**
     * Retrieves a ModuleFieldDto by ModuleRecord and ModuleField.
     *
     * @param moduleRecord
     * @param moduleField
     * @return
     */
    @Override
    public ModuleFieldDto getByModuleRecordAndModuleField(ModuleRecord moduleRecord, ModuleField moduleField) {

        Optional<RecordTransitionRelation> optionalRecordTransitionRelation = recordTransitionRelationRepository.findByRecordAndField(moduleRecord, moduleField);
        ModuleFieldDto moduleFieldDto = ModuleFieldDto.fromModuleField(moduleField);
        if (optionalRecordTransitionRelation.isEmpty()) {
            if (moduleField.isRequired()) {
                throw new DataNotFoundException("No transition relation found for record " + moduleRecord.getId() + " and field '" + moduleField.getName() + "'.");
            }
        } else {
            RecordTransitionRelation recordTransitionRelation = optionalRecordTransitionRelation.get();
            Transition transition = recordTransitionRelation.getTransition();
            Map<String, Object> data = Map.of(
                    "name", transition.getName(),
                    "score", transition.getScore()
            );
            moduleFieldDto.setData(data);
        }
        return moduleFieldDto;
    }

    /**
     * Deletes a record transition relation for a given ModuleRecord and ModuleField.
     *
     * @param moduleRecord
     * @param moduleField
     */
    @Override
    public void delete(ModuleRecord moduleRecord, ModuleField moduleField) {

        recordTransitionRelationRepository.deleteAllByRecordAndField(moduleRecord, moduleField);
    }

    /**
     * Deletes all record transition relations for a given Module.
     *
     * @param module
     */
    @Override
    public void deleteAllByModule(Module module) {

        recordTransitionRelationRepository.deleteAllByRecord_Module(module);
    }

    /**
     * Stores initial values on fields creation.
     *
     * @param moduleField
     * @param addModuleFieldDto
     * @return
     */
    @Override
    public ModuleFieldDto storeInitialValuesOnFieldsCreation(ModuleField moduleField, AddModuleFieldDto addModuleFieldDto) {

        List<Transition> transitions = addModuleFieldDto.getTransitions().stream().map(transitionDto -> transitionDto.toTransition(moduleField)).toList();
        transitionService.saveAll(transitions);
        List<TransitionDto> transitionDtos = transitions.stream().map(TransitionDto::fromTransition).toList();
        return ModuleFieldDto.fromModuleFieldWithData(moduleField, transitionDtos);
    }
}
