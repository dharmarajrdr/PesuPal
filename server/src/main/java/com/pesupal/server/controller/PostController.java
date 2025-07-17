package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreatePostDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.PostDto;
import com.pesupal.server.dto.response.PostsListDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.post.Post;
import com.pesupal.server.service.interfaces.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController extends CurrentValueRetriever {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createPost(@RequestBody CreatePostDto createPostDto) {

        Post post = postService.createPost(createPostDto, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Post created successfully", post));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> getPostById(@PathVariable Long postId) {

        PostDto post = postService.getPostByIdAndOrgId(postId, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Post retrieved successfully.", post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto> getPostsByUserId(@PathVariable(name = "userId") Long postOwnerId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {


        PostsListDto posts = postService.getPostByUserId(getCurrentUserId(), getCurrentOrgId(), postOwnerId, page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<ApiResponseDto> getPostsByTag(@PathVariable(name = "tag") String tag,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {


        PostsListDto posts = postService.getPostByTag(getCurrentUserId(), getCurrentOrgId(), "#" + tag, page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @PutMapping("/archive/{postId}")
    public ResponseEntity<ApiResponseDto> archivePost(@PathVariable Long postId) {

        postService.archivePost(postId, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Post archived successfully"));
    }
}
