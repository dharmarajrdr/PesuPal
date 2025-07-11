package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;

import java.util.List;

public interface FileService {
    
    List<FileOrFolderDto> findAllByFolderAndOrgMember(Folder parentFolder, OrgMember orgMember);
}
