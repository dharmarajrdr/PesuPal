package com.pesupal.server.dto.response;

import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostLikesDto extends UserBasicInfoDto {

    private LocalDateTime createdAt;

    public static PostLikesDto fromOrgMember(OrgMember orgMember) {

        PostLikesDto postLikesDto = new PostLikesDto();
        postLikesDto.setUserId(orgMember.getUser().getPublicId());
        postLikesDto.setDisplayName(orgMember.getDisplayName());
        postLikesDto.setDisplayPicture(orgMember.getDisplayPicture());
        return postLikesDto;
    }
}
