package com.pesupal.server.controller.post;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.post.QuoteDto;
import com.pesupal.server.service.interfaces.post.FeedsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/feeds")
public class FeedsController {

    private final FeedsService feedsService;

    @GetMapping("/quote-of-the-day")
    public ResponseEntity<ApiResponseDto> getQuoteOfTheDay() {

        QuoteDto quoteDto = feedsService.getQuoteOfTheDay();
        return ResponseEntity.ok().body(new ApiResponseDto("Quote fetched successfully", quoteDto));
    }
}
