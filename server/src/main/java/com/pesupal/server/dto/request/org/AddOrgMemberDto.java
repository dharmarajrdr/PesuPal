package com.pesupal.server.dto.request.org;

import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
public class AddOrgMemberDto {

    String email;

    String displayName;

    public void applyToOrgMember(OrgMember orgMember) {

        if (this.getDisplayName() != null) {
            orgMember.setDisplayName(this.displayName);
        }
    }
}
