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

    PostDto getPostByIdAndOrgId(Long postId);

    PostsListDto getPostByUserId(String postOwnerId, int page, int size, SortOrder sortOrder);

    void archivePost(Long postId);

    PostsListDto getPostByTag(String tag, int page, int size, SortOrder sortOrder);

    Post updatePost(Long postId, CreatePostDto createPostDto);

    void deletePost(Long postId);

    Post getPostByPublicId(String postId);
}
