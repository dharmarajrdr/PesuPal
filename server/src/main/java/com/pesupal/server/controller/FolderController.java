package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.FolderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/workdrive/")
public class FolderController extends CurrentValueRetriever {

    private final FolderService folderService;

    @PostMapping("/folder")
    public ResponseEntity<ApiResponseDto> createFolder(@RequestBody CreateFolderDto createFolderDto) {

        FolderDto folderDto = folderService.createFolder(createFolderDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Folder created successfully", folderDto));
    }

    @GetMapping("/{space}/folders")
    public ResponseEntity<ApiResponseDto> getAllFoldersInRoot(@PathVariable Workspace space) {

        List<FileOrFolderDto> folders = folderService.getAllFolders(space);
        return ResponseEntity.ok().body(new ApiResponseDto("Folders retrieved successfully", folders));
    }

    @GetMapping("/folders/{folderId}")
    public ResponseEntity<ApiResponseDto> getAllFolders(@PathVariable String folderId) {

        List<FileOrFolderDto> folders = folderService.getAllFolders(folderId);
        return ResponseEntity.ok().body(new ApiResponseDto("Folders retrieved successfully", folders));
    }

    @DeleteMapping("/folder/{folderId}")
    public ResponseEntity<ApiResponseDto> deleteFolder(@PathVariable Long folderId) {

        folderService.deleteFolder(folderId);
        return ResponseEntity.ok().body(new ApiResponseDto("Folder deleted successfully"));
    }
}
