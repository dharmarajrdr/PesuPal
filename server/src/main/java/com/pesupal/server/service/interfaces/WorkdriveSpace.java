package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;

import java.util.List;

public interface WorkdriveSpace {

    Folder save(Folder folder, CreateFolderDto createFolderDto, OrgMember orgMember);

    List<FileOrFolderDto> findAllFilesAndFoldersByOrgMember(OrgMember orgMember, Folder folder);
}
