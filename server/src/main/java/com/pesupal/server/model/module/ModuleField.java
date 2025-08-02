package com.pesupal.server.model.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ModuleField extends BaseModel {

    @ManyToOne
    @JsonIgnore
    private Module module;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Enumerated(EnumType.STRING)
    private FieldClassification classification = FieldClassification.USER_DEFINED_FIELD;

    @ElementCollection
    @CollectionTable(name = "module_field_restrict_from", joinColumns = @JoinColumn(name = "module_field_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<ModuleRole> restrictFrom;

    private boolean required;

    private boolean searchable;

    private boolean filterable;

    private boolean sortable;

    private boolean editable;

    private boolean showInList;

    private boolean showInDetail;
}
