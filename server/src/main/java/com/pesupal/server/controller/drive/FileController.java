package com.pesupal.server.controller.drive;

import com.pesupal.server.dto.request.drive.CreateFileDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.drive.FileDto;
import com.pesupal.server.dto.response.drive.SpaceStatDto;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.drive.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/{space}/stats")
    public ResponseEntity<ApiResponseDto> getSpaceStats(@PathVariable Workspace space) {

        Map<String, SpaceStatDto> stats = fileService.getSpaceStats(space);
        return ResponseEntity.ok().body(new ApiResponseDto("Space stats retrieved successfully", stats));
    }

    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<ApiResponseDto> deleteFile(@PathVariable String fileId) throws Exception {

        fileService.deleteFile(fileId);
        return ResponseEntity.ok().body(new ApiResponseDto("File deleted successfully", null));
    }
}
