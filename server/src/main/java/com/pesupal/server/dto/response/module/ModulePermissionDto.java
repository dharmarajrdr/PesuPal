package com.pesupal.server.dto.response.module;

import lombok.Data;

@Data
public class ModulePermissionDto {

    private String name;

    private boolean owner;

    private boolean maintainer;

    private boolean member;

    public ModulePermissionDto(String name) {

        this.name = name;
    }
}
