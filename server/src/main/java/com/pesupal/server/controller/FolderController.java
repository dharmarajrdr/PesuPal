package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.ApiResponseDto;
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

        FolderDto folderDto = folderService.createFolder(createFolderDto, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Folder created successfully", folderDto));
    }

    @GetMapping("/folders/{space}")
    public ResponseEntity<ApiResponseDto> getAllFolders(@PathVariable Workspace space) {

        List<FolderDto> folders = folderService.getAllFolders(space, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Folders retrieved successfully", folders));
    }

    @DeleteMapping("/folder/{folderId}")
    public ResponseEntity<ApiResponseDto> deleteFolder(@PathVariable Long folderId) {

        folderService.deleteFolder(folderId, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Folder deleted successfully"));
    }
}
