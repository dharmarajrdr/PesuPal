package com.pesupal.server.dto.request.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.module.ModuleRole;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateModuleMemberDto {

    private Long id;

    private ModuleRole role;

    public void applyToModuleMember(ModuleMember moduleMember) {

        if (role != null) {
            moduleMember.setRole(role);
        }
    }
}
