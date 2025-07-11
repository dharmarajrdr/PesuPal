package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.workdrive.Folder;

import java.util.List;

public interface FolderService {

    FolderDto createFolder(CreateFolderDto createFolderDto, Long userId, Long orgId);

    Folder getFolderById(Long folderId);

    Folder getFolderByIdAndOrgId(Long folderId, Long orgId);

    List<FolderDto> getAllFolders(Long parentFolderId, Long userId, Long orgId);

    List<FolderDto> getAllFolders(Workspace space, Long userId, Long orgId);

    void deleteFolder(Long folderId, Long userId, Long orgId);
}
