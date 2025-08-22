package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.WebsitePreviewDto;
import com.pesupal.server.service.interfaces.WebService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/web")
public class WebsiteController {

    private final WebService webService;

    @GetMapping("/preview")
    public ResponseEntity<ApiResponseDto> getWebsitePreview(@RequestParam String url) throws IOException {

        WebsitePreviewDto websitePreviewDto = webService.getWebsitePreview(url);
        return ResponseEntity.ok().body(new ApiResponseDto("Website preview fetched successfully", websitePreviewDto));
    }
}
