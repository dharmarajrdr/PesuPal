package com.pesupal.server.controller.post;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.post.TagDto;
import com.pesupal.server.service.interfaces.post.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping("/trending")
    public ResponseEntity<ApiResponseDto> getTrendingTags(@RequestParam(defaultValue = "10", required = false) int limit) {

        List<String> tags = tagService.getTrendingTags(limit);
        return ResponseEntity.ok(new ApiResponseDto("Trending tags fetched successfully", tags));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllTags(@RequestParam(defaultValue = "10", required = false) int size, @RequestParam(defaultValue = "0", required = false) int page) {


        List<TagDto> tags = tagService.getAllTags(size, page);
        return ResponseEntity.ok(new ApiResponseDto("Tags fetched successfully", tags));
    }

}
