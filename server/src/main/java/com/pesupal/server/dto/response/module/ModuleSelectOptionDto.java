package com.pesupal.server.dto.response.module;

import com.pesupal.server.model.module.ModuleSelectOption;
import lombok.Data;

@Data
public class ModuleSelectOptionDto {

    private Long id;

    private String value;

    private boolean isDefault;

    private boolean selected;

    public static ModuleSelectOptionDto fromModuleSelectOption(ModuleSelectOption moduleSelectOption) {

        ModuleSelectOptionDto dto = new ModuleSelectOptionDto();
        dto.setId(moduleSelectOption.getId());
        dto.setValue(moduleSelectOption.getValue());
        dto.setDefault(moduleSelectOption.isDefault());
        return dto;
    }

    public static ModuleSelectOptionDto fromModuleSelectOption(ModuleSelectOption moduleSelectOption, boolean selected) {

        ModuleSelectOptionDto dto = fromModuleSelectOption(moduleSelectOption);
        dto.setSelected(selected);
        return dto;
    }
}
