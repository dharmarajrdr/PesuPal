package com.pesupal.server.dto.response.org;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrgActionRolesDto {

    private OrgActionDto action;

    private List<OrgRoleDto> roles;
}
