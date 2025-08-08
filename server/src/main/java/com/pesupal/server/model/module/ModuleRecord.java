package com.pesupal.server.model.module;

import com.pesupal.server.model.PublicAccessModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ModuleRecord extends PublicAccessModel {

    @ManyToOne
    private Module module;
}
