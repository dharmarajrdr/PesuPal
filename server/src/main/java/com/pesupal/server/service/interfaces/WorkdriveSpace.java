package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;

public interface WorkdriveSpace {

    public Folder save(Folder folder, CreateFolderDto createFolderDto, OrgMember orgMember);
}
