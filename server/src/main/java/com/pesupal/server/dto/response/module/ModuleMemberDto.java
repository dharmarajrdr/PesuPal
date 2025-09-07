package com.pesupal.server.dto.response.module;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.module.ModuleMember;
import com.pesupal.server.model.module.ModuleRole;
import lombok.Data;

@Data
public class ModuleMemberDto {

    private Long id;

    private UserPreviewDto member;

    private ModuleRole role;

    public static ModuleMemberDto fromModuleMember(ModuleMember moduleMember) {

        ModuleMemberDto moduleMemberDto = new ModuleMemberDto();
        moduleMemberDto.setMember(UserPreviewDto.fromOrgMember(moduleMember.getOrgMember()));
        moduleMemberDto.setRole(moduleMember.getRole());
        moduleMemberDto.setId(moduleMember.getId());
        return moduleMemberDto;
    }
}
