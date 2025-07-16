package com.pesupal.server.dto.response;

import com.pesupal.server.model.post.PostReply;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyCommentDto extends UserBasicInfoDto {

    private Long id;

    private String message;

    private LocalDateTime createdAt;

    public static ReplyCommentDto fromPostReplyAndOrgMember(PostReply postReply, OrgMember orgMember) {

        ReplyCommentDto replyCommentDto = new ReplyCommentDto();
        replyCommentDto.setId(postReply.getId());
        replyCommentDto.setMessage(postReply.getMessage());
        replyCommentDto.setCreatedAt(postReply.getCreatedAt());
        replyCommentDto.setUserId(postReply.getReplier().getId());
        replyCommentDto.setDisplayName(orgMember.getDisplayName());
        replyCommentDto.setDisplayPicture(orgMember.getDisplayPicture());
        return replyCommentDto;
    }
}
