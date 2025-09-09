package com.pesupal.server.dto.response.org;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.org.OrgRole;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgRoleDto {

    private Long roleId;

    private String name;

    private String description;

    private UserPreviewDto createdBy;

    private Integer memberCount;

    public static OrgRoleDto fromOrgRole(OrgRole orgRole) {

        OrgRoleDto orgRoleDto = new OrgRoleDto();
        orgRoleDto.setRoleId(orgRole.getId());
        orgRoleDto.setDescription(orgRole.getDescription());
        orgRoleDto.setName(orgRole.getName());
        return orgRoleDto;
    }
}
