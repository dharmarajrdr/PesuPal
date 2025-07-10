package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.model.post.Post;

public interface PostService {

    Post createPost(CreatePostDto createPostDto, Long userId, Long orgId);

    PostDto getPostById(Long postId, Long userId, Long orgId);
}
