package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.StarredFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/workdrive")
public class StarredFileController {

    private final StarredFileService starredFileService;

    @PostMapping("/file/{fileId}/favourite")
    public ResponseEntity<ApiResponseDto> addStarredFile(@PathVariable Long fileId) {

        starredFileService.addStarredFile(fileId);
        return ResponseEntity.ok(new ApiResponseDto("File starred successfully"));
    }
}
