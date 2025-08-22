package com.pesupal.server.dto.response.module;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.module.Module;
import com.pesupal.server.model.user.OrgMember;
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

    private Boolean createRecord;

    private Boolean readRecord;

    private Boolean accessModuleBuilder;

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

    public static ModuleDto fromModuleWithOrgMember(Module module, OrgMember orgMember) {
        ModuleDto moduleDto = fromModule(module);
        moduleDto.setAccessModuleBuilder(module.getCreatedBy().getId().equals(orgMember.getId()));
        return moduleDto;
    }
}
