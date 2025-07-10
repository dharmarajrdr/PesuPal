package com.pesupal.server.controller;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.security.SecurityUtil;
import com.pesupal.server.service.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;
    private final SecurityUtil securityUtil;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createPost(@RequestBody CreatePostDto createPostDto) {

        Long userId = securityUtil.getCurrentUserId();
        Long orgId = RequestContext.getLong("X-ORG-ID");
        postService.createPost(createPostDto, userId, orgId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post created successfully"));
    }
}
