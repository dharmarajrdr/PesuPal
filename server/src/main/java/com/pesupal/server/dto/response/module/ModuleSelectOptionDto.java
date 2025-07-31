package com.pesupal.server.dto.response.module;

import com.pesupal.server.model.module.ModuleSelectOption;
import lombok.Data;

@Data
public class ModuleSelectOptionDto {

    private Long id;

    private String value;

    private boolean isDefault;

    public static ModuleSelectOptionDto fromModuleSelectOption(ModuleSelectOption moduleSelectOption) {

        ModuleSelectOptionDto dto = new ModuleSelectOptionDto();
        dto.setId(moduleSelectOption.getId());
        dto.setValue(moduleSelectOption.getValue());
        dto.setDefault(moduleSelectOption.isDefault());
        return dto;
    }
}
