package com.pesupal.server.dto.request.module;

import com.pesupal.server.model.module.Module;
import lombok.Data;

@Data
public class CreateModuleDto {

    private String name;

    private String description;
    
    public Module toModule() {

        Module module = new Module();
        module.setName(this.name);
        module.setDescription(this.description);
        return module;
    }
}
