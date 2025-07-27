package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateFileDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/workdrive")
public class FileController extends CurrentValueRetriever {

    private final FileService fileService;

    @PostMapping("/file")
    public ResponseEntity<ApiResponseDto> createFile(@RequestBody CreateFileDto createFileDto) throws Exception {

        FileDto fileDto = fileService.createFile(createFileDto);
        return ResponseEntity.ok().body(new ApiResponseDto("File created successfully", fileDto));
    }
}
