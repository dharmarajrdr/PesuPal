package com.pesupal.server.strategies.workspace;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.helpers.WorkspaceSupportsPublicFolder;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.PublicFolder;
import com.pesupal.server.repository.FolderRepository;
import com.pesupal.server.repository.PublicFolderRepository;
import com.pesupal.server.service.interfaces.WorkdriveSpace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component("ORG_SPACE")
@AllArgsConstructor
public class OrgSpace extends WorkspaceSupportsPublicFolder implements WorkdriveSpace {

    private final FolderRepository folderRepository;
    private final PublicFolderRepository publicFolderRepository;

    /**
     * Saves a folder in the organization space with security settings.
     *
     * @param folder
     * @return Folder
     */
    @Override
    public Folder save(Folder folder, CreateFolderDto createFolderDto, OrgMember orgMember) {

        folder = folderRepository.save(folder);
        PublicFolder publicFolder = getPublicFolder(folder, createFolderDto);
        publicFolderRepository.save(publicFolder);
        return folder;
    }
}
