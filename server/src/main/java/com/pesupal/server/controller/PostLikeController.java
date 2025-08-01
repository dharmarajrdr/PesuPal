package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.PostLikesDto;
import com.pesupal.server.service.interfaces.PostLikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post/like")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> getPostLikes(@PathVariable String postId) {

        List<PostLikesDto> likes = postLikeService.getPostLikes(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post likes retrieved successfully", likes));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> likePost(@PathVariable String postId) {

        postLikeService.likePost(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post liked successfully"));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> unlikePost(@PathVariable String postId) {

        postLikeService.unlikePost(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post unliked successfully"));
    }
}
