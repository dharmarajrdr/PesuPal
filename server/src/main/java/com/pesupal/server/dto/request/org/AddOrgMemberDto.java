package com.pesupal.server.dto.request.org;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.util.UUID;

@Data
public class AddOrgMemberDto {

    Long userId;

    String userName;

    String displayName;

    UUID displayPicture;

    Long designationId;

    Long departmentId;

    String managerId;

    Role role;

    public OrgMember toOrgMember() {

        OrgMember orgMember = new OrgMember();
        orgMember.setUserName(this.userName);
        orgMember.setDisplayName(this.displayName);
        orgMember.setDisplayPicture(this.displayPicture);
        orgMember.setRole(this.role);
        orgMember.setStatus("Away");    // Default status
        orgMember.setArchived(false);
        return orgMember;
    }

    public void applyToOrgMember(OrgMember orgMember) {

        if (this.getUserName() != null) {
            orgMember.setUserName(this.userName);
        }
        if (this.getDisplayName() != null) {
            orgMember.setDisplayName(this.displayName);
        }
        if (this.getDisplayPicture() != null) {
            orgMember.setDisplayPicture(this.displayPicture);
        }
    }
}
