package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.org.Org;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgDetailDto {

    private String publicId;

    private String displayName;

    private String uniqueName;

    private String displayPicture;

    private Role role;

    private Integer members;

    private LatestSubscriptionDto subscription;

    public static OrgDetailDto fromOrg(Org org) {

        OrgDetailDto dto = new OrgDetailDto();
        dto.setPublicId(org.getPublicId());
        dto.setDisplayName(org.getDisplayName());
        dto.setUniqueName(org.getUniqueName());
        dto.setDisplayPicture(org.getDisplayPicture());
        return dto;
    }

}
