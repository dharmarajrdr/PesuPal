package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePostCommentDto;
import com.pesupal.server.dto.response.PostCommentDto;
import com.pesupal.server.model.post.PostComment;

import java.util.List;

public interface PostCommentService {

    PostCommentDto createPostComment(CreatePostCommentDto createPostCommentDto);

    void deletePostComment(Long commentId);

    List<PostCommentDto> getPostComments(Long postId);

    PostComment getPostCommentById(Long commentId);
}
