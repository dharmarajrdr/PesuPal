package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.Arithmetic;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.workdrive.Folder;

import java.util.List;

public interface FolderService {

    FolderDto createFolder(CreateFolderDto createFolderDto);

    Folder getFolderById(Long folderId);

    Folder getFolderByPublicId(String folderId);

    List<FileOrFolderDto> getAllFolders(String folderId);

    List<FileOrFolderDto> getAllFolders(Workspace space);

    void deleteFolder(Long folderId);

    void updateFolderSizeRecursively(Folder folder, Long size, Arithmetic arithmetic);
}
