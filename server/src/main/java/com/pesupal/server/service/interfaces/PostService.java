package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.dto.response.PostsListDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.model.post.Post;

public interface PostService {

    Post createPost(CreatePostDto createPostDto, Long userId, Long orgId);
    
    Post getPostByIdAndOrgId(Long postId, Long orgId);

    PostDto getPostByIdAndOrgId(Long postId, Long userId, Long orgId);

    PostsListDto getPostByUserId(Long userId, Long orgId, Long postOwnerId, int page, int size, SortOrder sortOrder);

    void archivePost(Long postId, Long userId, Long orgId);

    PostsListDto getPostByTag(Long currentUserId, Long currentOrgId, String tag, int page, int size, SortOrder sortOrder);

    Post updatePost(Long postId, CreatePostDto createPostDto, Long currentUserId, Long currentOrgId);

    void deletePost(Long postId, Long userId, Long orgId);

    Post getPostByPublicId(String postId);
}
