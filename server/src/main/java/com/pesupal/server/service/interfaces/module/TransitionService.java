package com.pesupal.server.service.interfaces.module;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.Transition;

import java.util.List;

public interface TransitionService {

    Transition getByIdAndField(Long transitionId, ModuleField field);

    void saveAll(List<Transition> transitions);
}
