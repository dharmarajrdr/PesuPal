package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateFileDto;
import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;

import java.util.List;

public interface FileService {

    List<FileOrFolderDto> findAllByFolderAndOrgMember(Folder parentFolder, OrgMember orgMember);

    FileDto createFile(CreateFileDto createFileDto) throws Exception;

    File getFileByIdAndOrgId(Long fileId, Long orgId);
}
