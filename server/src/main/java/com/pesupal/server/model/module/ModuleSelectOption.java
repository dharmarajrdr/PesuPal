package com.pesupal.server.model.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ModuleSelectOption extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ModuleField moduleField;

    private String value;

    private boolean isDefault;
}
