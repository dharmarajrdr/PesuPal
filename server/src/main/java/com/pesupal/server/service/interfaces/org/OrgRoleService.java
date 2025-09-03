package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.response.org.OrgRoleDto;
import com.pesupal.server.model.org.OrgRole;
import com.pesupal.server.model.user.OrgMember;

public interface OrgRoleService {

    OrgRole createOrgRoleInternal(String name, OrgMember orgMember);

    OrgRole getRoleById(Long roleId);

    OrgRoleDto createOrgRole(String name, OrgMember currentOrgMember);

    void deleteRoleById(Long roleId, OrgMember orgMember);
}
