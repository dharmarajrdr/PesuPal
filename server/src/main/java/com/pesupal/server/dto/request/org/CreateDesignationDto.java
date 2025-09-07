package com.pesupal.server.dto.request.org;

import com.pesupal.server.model.user.Designation;
import lombok.Data;

@Data
public class CreateDesignationDto {

    private String name;

    private Long seniorityLevel;

    public Designation getDesignation() {

        Designation designation = new Designation();
        designation.setName(this.name);
        designation.setSeniorityLevel(this.seniorityLevel);
        return designation;
    }
}
