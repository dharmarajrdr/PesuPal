package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.request.post.CreateReplyCommentDto;
import com.pesupal.server.dto.response.post.ReplyCommentDto;

import java.util.List;

public interface PostReplyService {

    ReplyCommentDto createReplyComment(CreateReplyCommentDto createReplyCommentDto);

    List<ReplyCommentDto> getRepliesForComment(Long commentId);

    void deleteReply(Long replyId);

}
