package com.pesupal.server.dto.request.module;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.Transition;
import lombok.Data;

@Data
public class AddTransitionDto {

    private String name;

    private int score;

    public Transition toTransition() {

        Transition transition = new Transition();
        transition.setName(this.name);
        transition.setScore(this.score);
        return transition;
    }

    public Transition toTransition(ModuleField moduleField) {

        Transition transition = toTransition();
        transition.setField(moduleField);
        return transition;
    }
}
