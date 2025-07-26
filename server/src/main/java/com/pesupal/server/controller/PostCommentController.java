package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreatePostCommentDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.PostCommentDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.PostCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post")
public class PostCommentController extends CurrentValueRetriever {

    private final PostCommentService postCommentService;

    @GetMapping("/{postId}/comment")
    public ResponseEntity<ApiResponseDto> getPostComments(@PathVariable Long postId) {

        List<PostCommentDto> comments = postCommentService.getPostComments(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Comments retrieved successfully.", comments));
    }

    @PostMapping("/comment")
    public ResponseEntity<ApiResponseDto> createPostComment(@RequestBody CreatePostCommentDto createPostCommentDto) {

        PostCommentDto postCommentDto = postCommentService.createPostComment(createPostCommentDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Comment created successfully.", postCommentDto));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> deletePostComment(@PathVariable Long commentId) {

        postCommentService.deletePostComment(commentId);
        return ResponseEntity.ok().body(new ApiResponseDto("Comment deleted successfully."));
    }
}
