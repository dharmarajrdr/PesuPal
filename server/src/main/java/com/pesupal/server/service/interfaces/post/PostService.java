package com.pesupal.server.service.interfaces.post;

import com.pesupal.server.dto.request.post.CreatePostDto;
import com.pesupal.server.dto.response.post.PostDto;
import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.model.user.OrgMember;

import java.util.List;

public interface PostService {

    PostDto createPost(CreatePostDto createPostDto);

    PostDto schedulePost(CreatePostDto createPostDto);

    void unschedulePost(String postId, OrgMember triggeredBy);

    PostDto getPostDtoFromPostAndOrgMember(Post post, OrgMember orgMember);

    Post getPostByIdAndOrgId(Long postId, Long orgId);

    PostDto getPostByIdAndOrgId(String postId);

    Post getPostByPublicIdAndOrgId(String postId, Long orgId);

    PostsListDto getPostByUserId(String postOwnerId, int page, int size, SortOrder sortOrder);

    void archivePost(String postId);

    PostsListDto getPostByTag(String tag, int page, int size, SortOrder sortOrder);

    PostDto updatePost(String postId, CreatePostDto createPostDto);

    void deletePost(String postId);

    Post getPostByPublicId(String postId);

    PostsListDto getScheduledPosts(int page, int size, SortOrder sortOrder);

    PostsListDto getFeeds(int page, int size, SortOrder sortOrder);

    List<PostDto> getTrendingPosts(int limit);

    PostsListDto searchPosts(String query, int page, int size);
}
