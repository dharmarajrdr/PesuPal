package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.model.post.Post;

import java.util.List;

public interface PostService {

    Post createPost(CreatePostDto createPostDto, Long userId, Long orgId);

    Post getPostByIdAndOrgId(Long postId, Long orgId);

    PostDto getPostByIdAndOrgId(Long postId, Long userId, Long orgId);

    List<PostDto> getPostByUserId(Long userId, Long orgId, Long postOwnerId, int page, int size, SortOrder sortOrder);

    void archivePost(Long postId, Long userId, Long orgId);
}
