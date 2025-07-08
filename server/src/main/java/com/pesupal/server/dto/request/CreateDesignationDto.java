package com.pesupal.server.dto.request;

import com.pesupal.server.model.user.Designation;
import lombok.Data;

@Data
public class CreateDesignationDto {

    private String name;

    private Long seniorityLevel;

    private Long orgId;

    public Designation getDesignation() {

        Designation designation = new Designation();
        designation.setName(this.name);
        designation.setSeniorityLevel(this.seniorityLevel);
        return designation;
    }
}
