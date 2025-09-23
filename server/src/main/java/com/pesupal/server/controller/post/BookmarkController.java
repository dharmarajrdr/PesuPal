package com.pesupal.server.controller.post;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.service.interfaces.post.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/post")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmark/{postId}")
    public ResponseEntity<ApiResponseDto> createBookmark(@PathVariable String postId) {

        bookmarkService.createBookmark(postId);
        return ResponseEntity.ok(new ApiResponseDto("Post bookmarked successfully"));
    }

    @DeleteMapping("/bookmark/{postId}")
    public ResponseEntity<ApiResponseDto> removeBookmark(@PathVariable String postId) {

        bookmarkService.removeBookmark(postId);
        return ResponseEntity.ok(new ApiResponseDto("Bookmark removed successfully"));
    }

    @GetMapping("/bookmarks")
    public ResponseEntity<ApiResponseDto> getAllBookmarkedPosts(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {

        PostsListDto postsListDto = bookmarkService.getAllBookmarkedPosts(page, size);
        return ResponseEntity.ok(new ApiResponseDto("Bookmarked posts retrieved successfully", postsListDto.getPosts(), postsListDto.getInfo()));
    }
}
