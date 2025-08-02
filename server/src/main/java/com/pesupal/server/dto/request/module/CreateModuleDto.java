package com.pesupal.server.dto.request.module;

import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.module.ModuleAccessibility;
import lombok.Data;

@Data
public class CreateModuleDto {

    private String name;

    private String description;

    private ModuleAccessibility accessibility = ModuleAccessibility.ANYONE_IN_ORG;

    public Module toModule() {

        Module module = new Module();
        module.setName(name);
        module.setDescription(description);
        module.setAccessibility(accessibility);
        return module;
    }
}
