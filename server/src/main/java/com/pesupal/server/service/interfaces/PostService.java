package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePostDto;

public interface PostService {

    void createPost(CreatePostDto createPostDto, Long userId, Long orgId);
}
