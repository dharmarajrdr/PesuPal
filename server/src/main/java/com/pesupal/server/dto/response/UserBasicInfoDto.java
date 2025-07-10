package com.pesupal.server.dto.response;

import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
public class UserBasicInfoDto {

    private Long userId;

    private String displayName;

    private String displayPicture;

    public static UserBasicInfoDto fromOrgMember(OrgMember orgMember) {

        UserBasicInfoDto userBasicInfoDto = new UserBasicInfoDto();
        userBasicInfoDto.setUserId(orgMember.getUser().getId());
        userBasicInfoDto.setDisplayName(orgMember.getDisplayName());
        userBasicInfoDto.setDisplayPicture(orgMember.getDisplayPicture());
        return userBasicInfoDto;
    }
}
