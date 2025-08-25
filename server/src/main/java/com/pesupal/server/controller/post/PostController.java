package com.pesupal.server.controller.post;

import com.pesupal.server.dto.request.post.CreatePostDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.post.PostDto;
import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.enums.PostStatus;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.post.PostService;
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

        createPostDto.setStatus(PostStatus.PUBLISHED);
        PostDto post = postService.createPost(createPostDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Post created successfully", post));
    }

    @PostMapping("/schedule")
    public ResponseEntity<ApiResponseDto> schedulePost(@RequestBody CreatePostDto createPostDto) {

        createPostDto.setStatus(PostStatus.SCHEDULED);
        PostDto post = postService.schedulePost(createPostDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Post scheduled successfully", post));
    }

    @PatchMapping("/reschedule/{postId}")
    public ResponseEntity<ApiResponseDto> reschedulePost(@PathVariable String postId, @RequestBody CreatePostDto createPostDto) {

        createPostDto.setStatus(PostStatus.SCHEDULED);
        PostDto postDto = postService.updatePost(postId, createPostDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Post rescheduled successfully", postDto));
    }

    @PatchMapping("/unschedule/{postId}")
    public ResponseEntity<ApiResponseDto> unschedulePost(@PathVariable String postId) {

        postService.unschedulePost(postId, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Post unscheduled successfully"));
    }

    @GetMapping("/feeds")
    public ResponseEntity<ApiResponseDto> getFeeds(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {

        PostsListDto posts = postService.getFeeds(page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> getPostById(@PathVariable String postId) {

        PostDto post = postService.getPostByIdAndOrgId(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post retrieved successfully.", post));
    }

    @GetMapping("/scheduled")
    public ResponseEntity<ApiResponseDto> getScheduledPosts(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {

        PostsListDto posts = postService.getScheduledPosts(page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Scheduled posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
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

        PostDto postDto = postService.updatePost(postId, createPostDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Post updated successfully", postDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable String postId) {

        postService.deletePost(postId);
        return ResponseEntity.ok().body(new ApiResponseDto("Post deleted successfully"));
    }
}
