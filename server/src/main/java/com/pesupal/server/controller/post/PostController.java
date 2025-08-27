package com.pesupal.server.controller.post;

import com.pesupal.server.dto.request.post.CreatePostDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.post.PostDto;
import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.service.interfaces.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createPost(@RequestBody CreatePostDto createPostDto) {

        createPostDto.setStatus(PostStatus.PUBLISHED);
        PostDto post = postService.createPost(createPostDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Post created successfully", post));
    }

    @GetMapping("/feeds")
    public ResponseEntity<ApiResponseDto> getFeeds(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {

        PostsListDto posts = postService.getFeeds(page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> getPostById(@PathVariable String postId) {

        PostDto post = postService.getPostByIdAndOrgId(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post retrieved successfully.", post));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto> getPostsByUserId(@PathVariable(name = "userId") String postOwnerId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {


        PostsListDto posts = postService.getPostByUserId(postOwnerId, page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<ApiResponseDto> getPostsByTag(@PathVariable(name = "tag") String tag, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {


        PostsListDto posts = postService.getPostByTag("#" + tag, page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @GetMapping("/trending")
    public ResponseEntity<ApiResponseDto> getTrendingPosts(@RequestParam(defaultValue = "5", required = false) int limit) {

        List<PostDto> trendingPosts = postService.getTrendingPosts(limit);
        return ResponseEntity.ok().body(new ApiResponseDto("Trending posts retrieved successfully.", trendingPosts));
    }

    @PutMapping("/archive/{postId}")
    public ResponseEntity<ApiResponseDto> archivePost(@PathVariable String postId) {

        postService.archivePost(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post archived successfully"));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable String postId, @RequestBody CreatePostDto createPostDto) {

        PostDto postDto = postService.updatePost(postId, createPostDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Post updated successfully", postDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable String postId) {

        postService.deletePost(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post deleted successfully"));
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDto> searchPosts(@RequestParam String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        PostsListDto posts = postService.searchPosts(search, page, size);
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }
}
