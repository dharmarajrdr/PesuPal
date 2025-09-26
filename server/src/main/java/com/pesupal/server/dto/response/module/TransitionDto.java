package com.pesupal.server.dto.response.module;

import com.pesupal.server.model.module.Transition;
import lombok.Data;

@Data
public class TransitionDto {

    private Long id;

    private String name;

    private int score;

    public static TransitionDto fromTransition(Transition transition) {

        TransitionDto transitionDto = new TransitionDto();
        transitionDto.setId(transition.getId());
        transitionDto.setName(transition.getName());
        transitionDto.setScore(transition.getScore());
        return transitionDto;
    }
}
