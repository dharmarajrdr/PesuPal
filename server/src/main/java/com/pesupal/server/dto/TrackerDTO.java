package com.pesupal.server.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pesupal.server.enums.Days;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackerDTO {

    private Integer habitId;
    private Integer userId;
    private String title;
    private String description;
    private ArrayList<Days> frequency;
    private Date createdOn;
    private List<StreakDTO> streaks;

}
