package com.pesupal.server.dto.request.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddModuleFieldDto {

    private String moduleId;

    private String fieldName;

    private FieldType fieldType;

    private boolean required;

    private boolean searchable;

    private boolean filterable;

    private boolean sortable;

    private boolean editable;

    private boolean showInList;

    private boolean showInDetail;

    private List<AddModuleSelectOptionDto> options;

    public ModuleField toModuleField() {

        ModuleField moduleField = new ModuleField();
        moduleField.setName(fieldName);
        moduleField.setFieldType(fieldType);
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
