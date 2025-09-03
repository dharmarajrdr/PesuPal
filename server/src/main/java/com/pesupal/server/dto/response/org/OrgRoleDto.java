package com.pesupal.server.dto.response.org;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.org.OrgRole;
import lombok.Data;

@Data
public class OrgRoleDto {

    private Long roleId;

    private String name;

    private UserPreviewDto createdBy;

    public static OrgRoleDto fromOrgRole(OrgRole orgRole) {

        OrgRoleDto orgRoleDto = new OrgRoleDto();
        orgRoleDto.setRoleId(orgRole.getId());
        orgRoleDto.setName(orgRole.getName());
        return orgRoleDto;
    }
}
