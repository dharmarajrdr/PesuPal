package com.pesupal.server.service.implementations.module;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.Transition;
import com.pesupal.server.repository.TransitionRepository;
import com.pesupal.server.service.interfaces.module.TransitionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransitionServiceImpl implements TransitionService {

    private final TransitionRepository transitionRepository;

    /**
     * Retrieves a Transition by its ID and associated ModuleField.
     *
     * @param transitionId
     * @param field
     * @return
     */
    @Override
    public Transition getByIdAndField(Long transitionId, ModuleField field) {

        return transitionRepository.findByIdAndField(transitionId, field).orElseThrow(() -> new IllegalArgumentException("Transition with ID " + transitionId + " not found for the field '" + field.getName() + "'"));
    }

    /**
     * Saves a list of transitions to the database.
     *
     * @param transitions
     */
    @Override
    public void saveAll(List<Transition> transitions) {

        transitionRepository.saveAll(transitions);
    }
}
