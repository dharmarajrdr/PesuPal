package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePostCommentDto;
import com.pesupal.server.dto.response.PostCommentDto;
import com.pesupal.server.model.post.PostComment;

import java.util.List;

public interface PostCommentService {

    PostCommentDto createPostComment(CreatePostCommentDto createPostCommentDto, Long userId, Long orgId);

    void deletePostComment(Long commentId, Long currentUserId, Long currentOrgId);

    List<PostCommentDto> getPostComments(Long postId, Long userId, Long orgId);

    PostComment getPostCommentById(Long commentId, Long userId, Long orgId);
}
