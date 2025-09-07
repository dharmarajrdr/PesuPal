package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.request.post.CreatePostCommentDto;
import com.pesupal.server.dto.response.post.PostCommentDto;
import com.pesupal.server.model.post.PostComment;

import java.util.List;

public interface PostCommentService {

    PostCommentDto createPostComment(CreatePostCommentDto createPostCommentDto);

    void deletePostComment(Long commentId);

    List<PostCommentDto> getPostComments(String postId);

    PostComment getPostCommentById(Long commentId);

    void updateComment(Long commentId, CreatePostCommentDto updateCommentDto);
}
