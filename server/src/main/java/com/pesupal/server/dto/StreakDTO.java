package com.pesupal.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StreakDTO {

    private Integer streakId;
    private String streakDate;
    private String streakDay;
    private Integer habitId;
}
