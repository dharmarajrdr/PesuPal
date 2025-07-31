package com.pesupal.server.model.module;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ModuleSelectOption extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    private ModuleField moduleField;

    private String value;

    private boolean isDefault;
}
