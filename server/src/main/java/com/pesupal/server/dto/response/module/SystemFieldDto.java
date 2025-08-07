package com.pesupal.server.dto.response.module;

import com.pesupal.server.enums.FieldType;
import com.pesupal.server.model.module.FieldClassification;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SystemFieldDto {

    private String name;

    private FieldType fieldType;

    private boolean required;

    private boolean searchable;

    private boolean filterable;

    private boolean sortable;

    private boolean editable;   // by user

    private boolean showInList;

    private boolean showInDetail;

    public ModuleField toModuleField() {

        ModuleField moduleField = new ModuleField();
        moduleField.setName(name);
        moduleField.setFieldType(fieldType);
        moduleField.setClassification(FieldClassification.SYSTEM_FIELD);
        moduleField.setRequired(required);
        moduleField.setSearchable(searchable);
        moduleField.setFilterable(filterable);
        moduleField.setSortable(sortable);
        moduleField.setEditable(editable);
        moduleField.setShowInList(showInList);
        moduleField.setShowInDetail(showInDetail);
        return moduleField;
    }

    public ModuleField toModuleField(Module module) {

        ModuleField moduleField = toModuleField();
        moduleField.setModule(module);
        return moduleField;
    }
}
