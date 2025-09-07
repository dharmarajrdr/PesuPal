package com.pesupal.server.dto.response.org;

import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
public class OrgCreatedDto {

    private String orgId;

    private String orgMemberId;

    public static OrgCreatedDto fromOrgMember(OrgMember orgMember) {

        OrgCreatedDto dto = new OrgCreatedDto();
        dto.setOrgId(orgMember.getOrg().getPublicId());
        dto.setOrgMemberId(orgMember.getPublicId());
        return dto;
    }
}
