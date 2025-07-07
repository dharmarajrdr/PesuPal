package com.pesupal.server.model.user;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.org.Org;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Designation extends BaseModel {

    @ManyToOne
    private Org org;

    private String name;

    private Long seniorityLevel;
}
