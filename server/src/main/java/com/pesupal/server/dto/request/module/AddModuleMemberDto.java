package com.pesupal.server.dto.request.module;

import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.module.ModuleRole;
import lombok.Data;

@Data
public class AddModuleMemberDto {

    private String userId;

    private String moduleId;

    private ModuleRole role;

    public ModuleMember toModuleMember() {

        ModuleMember moduleMember = new ModuleMember();
        moduleMember.setRole(role);
        moduleMember.setActive(true);
        return moduleMember;
    }
}
