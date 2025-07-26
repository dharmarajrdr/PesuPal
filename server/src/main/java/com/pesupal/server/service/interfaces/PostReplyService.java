package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateReplyCommentDto;
import com.pesupal.server.dto.response.ReplyCommentDto;

import java.util.List;

public interface PostReplyService {

    ReplyCommentDto createReplyComment(CreateReplyCommentDto createReplyCommentDto);

    List<ReplyCommentDto> getRepliesForComment(Long commentId);

    void deleteReply(Long replyId);

}
