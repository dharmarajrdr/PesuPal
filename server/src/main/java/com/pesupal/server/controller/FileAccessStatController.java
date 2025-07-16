package com.pesupal.server.controller;

import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.FileAccessStatDto;
import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.FileAccessStatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/workdrive")
public class FileAccessStatController extends CurrentValueRetriever {

    private final FileAccessStatService fileAccessStatService;

    @GetMapping("/file-access-stat/{fileId}")
    public ResponseEntity<ApiResponseDto> getFileAccessStats(@PathVariable Long fileId) {

        List<FileAccessStatDto> fileAccessStatDto = fileAccessStatService.getFileAccessStats(fileId, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok(new ApiResponseDto("File access statistics retrieved successfully", fileAccessStatDto));
    }

    @GetMapping("/recently-accessed-files")
    public ResponseEntity<ApiResponseDto> getRecentlyAccessedFiles() {

        List<FileDto> recentlyAccessedFiles = fileAccessStatService.getRecentlyAccessedFiles(getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok(new ApiResponseDto("Recently accessed files retrieved successfully", recentlyAccessedFiles));
    }
}
