package com.pesupal.server.service.interfaces.drive;

import com.pesupal.server.dto.request.drive.CreateFolderDto;
import com.pesupal.server.dto.response.drive.FileOrFolderDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;

import java.util.List;

public interface WorkdriveSpace {

    Folder save(Folder folder, CreateFolderDto createFolderDto, OrgMember orgMember);

    List<FileOrFolderDto> findAllFilesAndFoldersByOrgMemberAndFolder(OrgMember orgMember, Folder folder);
}
