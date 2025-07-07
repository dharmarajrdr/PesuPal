package com.pesupal.server.model.department;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Department extends CreationTimeAuditable {

    private String name;

    private String description;

    @ManyToOne
    private Org org;
}
