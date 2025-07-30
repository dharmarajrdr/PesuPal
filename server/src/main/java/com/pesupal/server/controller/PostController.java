package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.dto.response.PostsListDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.service.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createPost(@RequestBody CreatePostDto createPostDto) {

        Post post = postService.createPost(createPostDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Post created successfully", post));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> getPostById(@PathVariable String postId) {

        PostDto post = postService.getPostByIdAndOrgId(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post retrieved successfully.", post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto> getPostsByUserId(@PathVariable(name = "userId") String postOwnerId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {


        PostsListDto posts = postService.getPostByUserId(postOwnerId, page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<ApiResponseDto> getPostsByTag(@PathVariable(name = "tag") String tag,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {


        PostsListDto posts = postService.getPostByTag("#" + tag, page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @PutMapping("/archive/{postId}")
    public ResponseEntity<ApiResponseDto> archivePost(@PathVariable String postId) {

        postService.archivePost(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post archived successfully"));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable String postId, @RequestBody CreatePostDto createPostDto) {

        Post post = postService.updatePost(postId, createPostDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Post updated successfully", post));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable String postId) {

        postService.deletePost(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post deleted successfully"));
    }
}
