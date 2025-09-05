package com.pesupal.server.dto.request.org;

import com.pesupal.server.model.org.OrgRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRoleDto {

    private String name;

    private String description;

    public OrgRole toOrgRole() {

        OrgRole orgRole = new OrgRole();
        orgRole.setName(this.name);
        orgRole.setDescription(this.description);
        return orgRole;
    }

    public void applyOrgRole(OrgRole orgRole) {

        if (this.name != null) {
            orgRole.setName(this.name);
        }
        if (this.description != null) {
            orgRole.setDescription(this.description);
        }
    }
}
