package com.pesupal.server.dto.request.module;

import com.pesupal.server.model.module.ModuleRole;
import lombok.Data;

@Data
public class UpdateModulePermissionDto {

    private String moduleId;

    private ModuleRole role;

    private String name;

    private boolean enable;
}
