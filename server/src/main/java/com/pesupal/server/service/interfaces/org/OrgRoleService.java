package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.request.org.CreateRoleDto;
import com.pesupal.server.dto.response.org.OrgRoleDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgRole;
import com.pesupal.server.model.user.OrgMember;

import java.util.List;

public interface OrgRoleService {

    OrgRole createOrgRoleInternal(CreateRoleDto createRoleDto, OrgMember orgMember);

    OrgRole getRoleById(Long roleId);

    OrgRoleDto createOrgRole(CreateRoleDto createRoleDto, OrgMember currentOrgMember);

    void deleteRoleById(Long roleId, OrgMember orgMember);

    OrgRole getRoleByOrgAndName(Org org, String superAdmin);

    List<OrgRoleDto> getAllRoles(OrgMember currentOrgMember);

    OrgRoleDto updateOrgRole(Long roleId, CreateRoleDto updateRoleDto, OrgMember currentOrgMember);
}
