package com.pesupal.server.dto.response.module;

import com.pesupal.server.model.module.Module;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ModulePreviewDto {

    private String id;

    private String name;

    private LocalDateTime createdAt;

    private boolean active;

    private Map<String, String> accessibility;

    public static ModulePreviewDto fromModule(Module module) {

        if (module == null) {
            return null;
        }

        ModulePreviewDto dto = new ModulePreviewDto();
        dto.setId(module.getPublicId());
        dto.setName(module.getName());
        dto.setCreatedAt(module.getCreatedAt());
        dto.setActive(module.isActive());
        dto.setAccessibility(Map.of("name", module.getAccessibility().name(), "description", module.getAccessibility().getDescription()));
        return dto;
    }
}
