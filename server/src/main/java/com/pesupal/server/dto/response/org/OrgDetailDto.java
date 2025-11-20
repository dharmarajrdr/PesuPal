package com.pesupal.server.dto.response.org;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgRole;
import lombok.Data;

import java.net.URL;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgDetailDto {

    private String publicId;

    private String displayName;

    private String uniqueName;

    private URL displayPicture;

    private OrgRole role;

    private Integer members;

    private LatestSubscriptionDto subscription;

    public static OrgDetailDto fromOrg(Org org) {

        OrgDetailDto dto = new OrgDetailDto();
        dto.setPublicId(org.getPublicId());
        dto.setDisplayName(org.getDisplayName());
        dto.setUniqueName(org.getUniqueName());
        return dto;
    }

}
