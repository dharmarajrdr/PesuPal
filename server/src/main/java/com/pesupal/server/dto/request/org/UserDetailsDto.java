package com.pesupal.server.dto.request.org;

import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDetailsDto {

    private String displayName;

    private UUID displayPicture;

    public OrgMember toOrgMember() {

        OrgMember orgMember = new OrgMember();
        orgMember.setDisplayName(this.displayName);
        orgMember.setDisplayPicture(this.displayPicture);
        return orgMember;
    }
}
