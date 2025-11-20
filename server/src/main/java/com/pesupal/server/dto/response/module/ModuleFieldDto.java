package com.pesupal.server.dto.response.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.FieldType;
import com.pesupal.server.model.module.FieldClassification;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleField;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleFieldDto<T> {

    private Long fieldId;

    private String fieldName;

    private FieldType fieldType;

    private FieldClassification classification;

    private boolean required;

    private boolean searchable;

    private boolean filterable;

    private boolean sortable;

    private boolean editable;

    private boolean showInList;

    private boolean showInDetail;

    private T data;

    public static ModuleFieldDto fromModuleField(ModuleField moduleField) {

        return ModuleFieldDto.builder().fieldId(moduleField.getId()).fieldName(moduleField.getName()).fieldType(moduleField.getFieldType()).classification(moduleField.getClassification()).required(moduleField.isRequired()).searchable(moduleField.isSearchable()).filterable(moduleField.isFilterable()).sortable(moduleField.isSortable()).editable(moduleField.isEditable()).showInList(moduleField.isShowInList()).showInDetail(moduleField.isShowInDetail()).build();
    }

    public ModuleField toModuleField(Module module) {

        ModuleField moduleField = new ModuleField();
        moduleField.setId(this.fieldId);
        moduleField.setName(this.fieldName);
        moduleField.setFieldType(this.fieldType);
        moduleField.setClassification(this.classification);
        moduleField.setRequired(this.required);
        moduleField.setSearchable(this.searchable);
        moduleField.setFilterable(this.filterable);
        moduleField.setSortable(this.sortable);
        moduleField.setEditable(this.editable);
        moduleField.setShowInList(this.showInList);
        moduleField.setShowInDetail(this.showInDetail);
        moduleField.setModule(module);
        return moduleField;
    }

    public static ModuleFieldDto fromModuleFieldWithData(ModuleField moduleField, Object data) {

        ModuleFieldDto dto = fromModuleField(moduleField);
        dto.setData(data);
        return dto;
    }

}
