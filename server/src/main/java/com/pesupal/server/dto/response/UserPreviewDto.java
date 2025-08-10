package com.pesupal.server.dto.response;

import com.pesupal.server.model.user.OrgMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPreviewDto {

    private String id;

    private String displayName;

    private String displayPicture;

    private boolean archived;

    public static UserPreviewDto fromOrgMember(OrgMember orgMember) {

        if (orgMember == null) {
            return null;
        }

        UserPreviewDto userPreviewDto = new UserPreviewDto();
        userPreviewDto.setId(orgMember.getPublicId());
        userPreviewDto.setDisplayName(orgMember.getDisplayName());
        userPreviewDto.setDisplayPicture(orgMember.getDisplayPicture());
        userPreviewDto.setArchived(orgMember.isArchived());
        return userPreviewDto;
    }
}
