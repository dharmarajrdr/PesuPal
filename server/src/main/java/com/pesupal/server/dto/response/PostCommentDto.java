package com.pesupal.server.dto.response;

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

    private boolean deleteable;

    public static PostCommentDto fromPostCommentAndOrgMember(PostComment postComment, OrgMember orgMember) {

        PostCommentDto dto = new PostCommentDto();
        dto.setId(postComment.getId());
        dto.setMessage(postComment.getMessage());
        dto.setCreatedAt(postComment.getCreatedAt());
        dto.setReplyCount(postComment.getReplies().size());
        dto.setUserId(postComment.getCommenter().getId());
        dto.setDisplayName(orgMember.getDisplayName());
        dto.setDisplayPicture(orgMember.getDisplayPicture());
        return dto;
    }
}
