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
public class PostScheduleController extends CurrentValueRetriever {

    private final PostService postService;

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

    @GetMapping("/scheduled")
    public ResponseEntity<ApiResponseDto> getScheduledPosts(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {

        PostsListDto posts = postService.getScheduledPosts(page, size, SortOrder.valueOf(sortOrder));
        return ResponseEntity.ok().body(new ApiResponseDto("Scheduled posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }
}
