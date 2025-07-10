package com.pesupal.server.controller;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.security.SecurityUtil;
import com.pesupal.server.service.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Post post = postService.createPost(createPostDto, userId, orgId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post created successfully", post));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> getPostById(@PathVariable Long postId) {
        Long userId = securityUtil.getCurrentUserId();
        Long orgId = RequestContext.getLong("X-ORG-ID");
        PostDto post = postService.getPostById(postId, userId, orgId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post retrieved successfully.", post));
    }
}
