package com.pesupal.server.dto.response.module;

import com.pesupal.server.model.module.ModuleSelectOption;
import lombok.Data;

@Data
public class ModuleSelectOptionDto {

    private String value;

    private boolean isDefault;

    public static ModuleSelectOptionDto fromModuleSelectOption(ModuleSelectOption moduleSelectOption) {

        ModuleSelectOptionDto dto = new ModuleSelectOptionDto();
        dto.setValue(moduleSelectOption.getValue());
        dto.setDefault(moduleSelectOption.isDefault());
        return dto;
    }
}
