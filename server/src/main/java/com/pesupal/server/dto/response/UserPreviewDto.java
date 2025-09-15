package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.MemberStatus;
import com.pesupal.server.model.user.OrgMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPreviewDto {

    private String id;

    private String displayName;

    private URL displayPicture;

    private boolean archived;

    private String chatId;

    private MemberStatus status;

    public static UserPreviewDto fromOrgMember(OrgMember orgMember) {

        if (orgMember == null) {
            return null;
        }

        UserPreviewDto userPreviewDto = new UserPreviewDto();
        userPreviewDto.setId(orgMember.getPublicId());
        userPreviewDto.setDisplayName(orgMember.getDisplayName());
        userPreviewDto.setArchived(orgMember.isArchived());
        userPreviewDto.setStatus(orgMember.getStatus());
        return userPreviewDto;
    }
}
