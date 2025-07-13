package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBasicInfoDto {

    private Long userId;

    private String displayName;

    private String displayPicture;

    private String designation;

    private String status;

    public static UserBasicInfoDto fromOrgMember(OrgMember orgMember) {

        UserBasicInfoDto userBasicInfoDto = new UserBasicInfoDto();
        userBasicInfoDto.setUserId(orgMember.getUser().getId());
        userBasicInfoDto.setDisplayName(orgMember.getDisplayName());
        userBasicInfoDto.setDisplayPicture(orgMember.getDisplayPicture());
        userBasicInfoDto.setDesignation(orgMember.getDesignation().getName());
        userBasicInfoDto.setStatus(orgMember.getStatus());
        return userBasicInfoDto;
    }
}
