package com.pesupal.server.dto.response.module;

import com.pesupal.server.model.module.Module;
import lombok.Data;

@Data
public class ModulePreviewDto {

    private String id;

    private String name;

    private boolean active;

    public static ModulePreviewDto fromModule(Module module) {

        if (module == null) {
            return null;
        }

        ModulePreviewDto dto = new ModulePreviewDto();
        dto.setId(module.getPublicId());
        dto.setName(module.getName());
        dto.setActive(module.isActive());
        return dto;
    }
}
