package com.pesupal.server.dto.response.module;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.module.Module;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModuleDto {

    private String publicId;

    private String name;

    private String description;

    private UserPreviewDto createdBy;

    private LocalDateTime createdAt;

    private boolean active;

    private boolean allowDuplicateSubject;

    private boolean openToRelation;

    public static ModuleDto fromModule(Module module) {

        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setName(module.getName());
        moduleDto.setDescription(module.getDescription());
        moduleDto.setCreatedBy(UserPreviewDto.fromOrgMember(module.getCreatedBy()));
        moduleDto.setActive(module.isActive());
        moduleDto.setCreatedAt(module.getCreatedAt());
        moduleDto.setPublicId(module.getPublicId());
        moduleDto.setAllowDuplicateSubject(module.isAllowDuplicateSubject());
        return moduleDto;
    }
}
