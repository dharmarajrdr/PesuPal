package com.pesupal.server.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pesupal.server.enums.Days;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date streakDate;
    
    private Days streakDay;
    
    private Integer habitId;
}
