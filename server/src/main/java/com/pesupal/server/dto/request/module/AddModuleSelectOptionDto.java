package com.pesupal.server.dto.request.module;

import com.pesupal.server.model.module.ModuleField;
import com.pesupal.server.model.module.ModuleSelectOption;
import lombok.Data;

@Data
public class AddModuleSelectOptionDto {

    private String value;

    private boolean isDefault;

    public ModuleSelectOption toModuleSelectOption() {

        ModuleSelectOption moduleSelectOption = new ModuleSelectOption();
        moduleSelectOption.setValue(this.value);
        moduleSelectOption.setDefault(this.isDefault);
        return moduleSelectOption;
    }

    public ModuleSelectOption toModuleSelectOption(ModuleField moduleField) {

        ModuleSelectOption moduleSelectOption = toModuleSelectOption();
        moduleSelectOption.setModuleField(moduleField);
        return moduleSelectOption;
    }
}
