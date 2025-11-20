package com.pesupal.server.dto.response.post;

import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.model.post.PostComment;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCommentDto extends UserBasicInfoDto {

    private Long id;

    private String message;

    private LocalDateTime createdAt;

    private int replyCount;

    private boolean deletable;

    public static PostCommentDto fromPostCommentAndOrgMember(PostComment postComment, OrgMember orgMember) {

        PostCommentDto dto = new PostCommentDto();
        dto.setId(postComment.getId());
        dto.setMessage(postComment.getMessage());
        dto.setCreatedAt(postComment.getCreatedAt());
        dto.setReplyCount(postComment.getReplies().size());
        if (postComment.isAnonymous()) {
            dto.setDisplayName("Anonymous");
            // dto.setDisplayPicture("/images/anonymous.jpg");
        } else {
            dto.setDisplayName(orgMember.getDisplayName());
            dto.setUserId(postComment.getCommenter().getPublicId());
        }
        return dto;
    }
}
