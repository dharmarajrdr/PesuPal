package com.pesupal.server.dto.response;

import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
public class UserPreviewDto {

    private Long id;

    private String displayName;

    private String displayPicture;

    public static UserPreviewDto fromOrgMember(OrgMember orgMember) {

        UserPreviewDto userPreviewDto = new UserPreviewDto();
        userPreviewDto.setId(orgMember.getUser().getId());
        userPreviewDto.setDisplayName(orgMember.getDisplayName());
        userPreviewDto.setDisplayPicture(orgMember.getDisplayPicture());
        return userPreviewDto;
    }
}
