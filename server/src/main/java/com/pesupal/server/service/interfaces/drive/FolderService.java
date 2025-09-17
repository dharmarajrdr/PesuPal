package com.pesupal.server.service.interfaces.drive;

import com.pesupal.server.dto.request.drive.CreateFolderDto;
import com.pesupal.server.dto.response.drive.FileOrFolderDto;
import com.pesupal.server.dto.response.drive.FolderDto;
import com.pesupal.server.dto.response.drive.FolderPreviewDto;
import com.pesupal.server.enums.Arithmetic;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;

import java.util.List;

public interface FolderService {

    FolderDto createFolder(CreateFolderDto createFolderDto, OrgMember orgMember);

    Folder getFolderByPublicId(String folderId);

    List<FileOrFolderDto> getAllFolders(String folderId);

    List<FileOrFolderDto> getAllFolders(Workspace space);

    void deleteFolder(String folderId);

    void restoreFolder(String folderId);

    void clearFolder(String folderId);

    void updateFolderSizeRecursively(Folder folder, Long size, Arithmetic arithmetic);

    List<FolderPreviewDto> getParentFolders(String folderId);
}
