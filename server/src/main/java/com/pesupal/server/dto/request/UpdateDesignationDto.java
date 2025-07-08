package com.pesupal.server.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.user.Designation;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateDesignationDto {

    private String name;

    private Long seniorityLevel;

    public void copy(Designation designation) {
        designation.setName(this.getName());
        designation.setSeniorityLevel(this.getSeniorityLevel());
    }
}
