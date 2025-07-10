package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateReplyCommentDto;
import com.pesupal.server.dto.response.ReplyCommentDto;

import java.util.List;

public interface PostReplyService {

    ReplyCommentDto createReplyComment(CreateReplyCommentDto createReplyCommentDto, Long userId, Long orgId);

    List<ReplyCommentDto> getRepliesForComment(Long commentId, Long userId, Long orgId);

    void deleteReply(Long replyId, Long userId, Long orgId);

}
