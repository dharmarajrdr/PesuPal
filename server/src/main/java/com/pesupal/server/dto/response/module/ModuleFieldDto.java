package com.pesupal.server.dto.response.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.model.module.ModuleField;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleFieldDto {

    private String fieldName;

    private FieldType fieldType;

    private boolean required;

    private boolean searchable;

    private boolean filterable;

    private boolean sortable;

    private boolean editable;

    private boolean showInList;

    private boolean showInDetail;

    private List<ModuleSelectOptionDto> options;

    public static ModuleFieldDto fromModuleField(ModuleField moduleField) {

        ModuleFieldDto dto = new ModuleFieldDto();
        dto.setFieldName(moduleField.getName());
        dto.setFieldType(moduleField.getFieldType());
        dto.setRequired(moduleField.isRequired());
        dto.setSearchable(moduleField.isSearchable());
        dto.setFilterable(moduleField.isFilterable());
        dto.setSortable(moduleField.isSortable());
        dto.setEditable(moduleField.isEditable());
        dto.setShowInList(moduleField.isShowInList());
        dto.setShowInDetail(moduleField.isShowInDetail());
        return dto;
    }

    public static ModuleFieldDto fromModuleFieldWithOptions(ModuleField moduleField, List<ModuleSelectOptionDto> options) {

        ModuleFieldDto dto = fromModuleField(moduleField);
        dto.setOptions(options);
        return dto;
    }

}
