package com.pesupal.server.dto.response.org;

import com.pesupal.server.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPresenceUpdateDto {

    private String orgId;

    private String orgMemberId;

    private MemberStatus memberStatus;

    public String toString() {

        return "User with ID " + orgMemberId + " in org " + orgId + " is now " + memberStatus;
    }
}
