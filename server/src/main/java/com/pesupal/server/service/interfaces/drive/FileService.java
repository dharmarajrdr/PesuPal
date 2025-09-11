package com.pesupal.server.service.interfaces.drive;

import com.pesupal.server.dto.request.drive.CreateFileDto;
import com.pesupal.server.dto.response.drive.FileDto;
import com.pesupal.server.dto.response.drive.FileOrFolderDto;
import com.pesupal.server.dto.response.drive.SpaceStatDto;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;

import java.util.List;
import java.util.Map;

public interface FileService {

    FileDto fromFileAndOrgMember(File file, OrgMember owner);

    List<FileOrFolderDto> findAllByFolderAndOrgMemberAndDeleted(Folder parentFolder, OrgMember orgMember, boolean deleted);

    FileDto createFile(CreateFileDto createFileDto) throws Exception;

    File getFileByIdAndOrgId(Long fileId, Long orgId);

    File getFileByPublicId(String publicId);

    Map<String, SpaceStatDto> getSpaceStats(Workspace space);

    void deleteFile(String fileId);

    void deleteAllByOrg(Org deletedOrg);
}
