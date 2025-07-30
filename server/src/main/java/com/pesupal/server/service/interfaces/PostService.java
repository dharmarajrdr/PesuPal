package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.dto.response.PostsListDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.post.Post;

public interface PostService {

    Post createPost(CreatePostDto createPostDto);

    Post getPostByIdAndOrgId(Long postId, Long orgId);

    Post getPostByIdAndOrg(Long postId, Org org);

    PostDto getPostByIdAndOrgId(String postId);

    Post getPostByPublicIdAndOrgId(String postId, Long orgId);

    PostsListDto getPostByUserId(String postOwnerId, int page, int size, SortOrder sortOrder);

    void archivePost(String postId);

    PostsListDto getPostByTag(String tag, int page, int size, SortOrder sortOrder);

    Post updatePost(String postId, CreatePostDto createPostDto);

    void deletePost(String postId);

    Post getPostByPublicId(String postId);
}
