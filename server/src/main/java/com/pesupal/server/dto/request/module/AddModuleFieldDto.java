package com.pesupal.server.dto.request.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleRole;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Set<ModuleRole> restrictFrom = new HashSet<>();

    private List<AddModuleSelectOptionDto> options;

    public ModuleField toModuleField() {

        List<ModuleRole> restrictFromList = restrictFrom.stream().toList();
        if (!restrictFromList.isEmpty() && required) {
            throw new ActionProhibitedException("You cannot restrict a field from roles and make it required at the same time.");
        }

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
        moduleField.setRestrictFrom(restrictFromList);
        return moduleField;
    }

    public ModuleField toModuleField(Module module) {

        ModuleField moduleField = toModuleField();
        moduleField.setModule(module);
        return moduleField;
    }
}
