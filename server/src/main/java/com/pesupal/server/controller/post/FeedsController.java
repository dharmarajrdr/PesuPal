package com.pesupal.server.controller.post;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.post.PostsListDto;
import com.pesupal.server.dto.response.post.QuoteDto;
import com.pesupal.server.enums.SortOrder;
import com.pesupal.server.service.interfaces.post.FeedsService;
import com.pesupal.server.service.interfaces.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/feeds")
public class FeedsController {

    private final PostService postService;
    private final FeedsService feedsService;

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getFeeds(@RequestParam(required = false, defaultValue = "") String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(name = "sort_order", defaultValue = "DESC") String sortOrder) {

        PostsListDto posts = search.trim().length() <= 3 ? feedsService.getFeeds(page, size, SortOrder.valueOf(sortOrder)) : postService.searchPosts(search, page, size);
        return ResponseEntity.ok().body(new ApiResponseDto("Posts retrieved successfully.", posts.getPosts(), posts.getInfo()));
    }

    @GetMapping("/quote-of-the-day")
    public ResponseEntity<ApiResponseDto> getQuoteOfTheDay() {

        QuoteDto quoteDto = feedsService.getQuoteOfTheDay();
        return ResponseEntity.ok().body(new ApiResponseDto("Quote fetched successfully", quoteDto));
    }
}
