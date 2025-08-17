package com.pesupal.server.strategies.workspace;

import com.pesupal.server.dto.request.drive.CreateFolderDto;
import com.pesupal.server.dto.response.drive.FileOrFolderDto;
import com.pesupal.server.dto.response.drive.FolderDto;
import com.pesupal.server.enums.CRUD;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.helpers.WorkspaceSupportsPublicFolder;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.PublicFolder;
import com.pesupal.server.repository.drive.FolderRepository;
import com.pesupal.server.repository.drive.PublicFolderRepository;
import com.pesupal.server.service.interfaces.drive.FileService;
import com.pesupal.server.service.interfaces.drive.PublicFolderService;
import com.pesupal.server.service.interfaces.drive.SecuredFolderPermissionService;
import com.pesupal.server.service.interfaces.drive.WorkdriveSpace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("ORG_SPACE")
@AllArgsConstructor
public class OrgSpace extends WorkspaceSupportsPublicFolder implements WorkdriveSpace {

    private final FileService fileService;
    private final FolderRepository folderRepository;
    private final PublicFolderService publicFolderService;
    private final PublicFolderRepository publicFolderRepository;
    private final SecuredFolderPermissionService securedFolderPermissionService;

    /**
     * Saves a folder in the organization space with security settings.
     *
     * @param folder
     * @return Folder
     */
    @Override
    public Folder save(Folder folder, CreateFolderDto createFolderDto, OrgMember orgMember) {

        ensureNecessaryPermissionInsideSecuredFolder(folder.getParentFolder(), orgMember, CRUD.CREATE, securedFolderPermissionService, publicFolderService);

        folder = folderRepository.save(folder);
        PublicFolder publicFolder = getPublicFolder(folder, createFolderDto);
        publicFolderRepository.save(publicFolder);
        return folder;
    }

    /**
     * Finds all folders by the organization member in the organization space.
     *
     * @param orgMember
     * @param parentFolder
     * @return
     */
    @Override
    public List<FileOrFolderDto> findAllFilesAndFoldersByOrgMemberAndFolder(OrgMember orgMember, Folder parentFolder) {

        ensureNecessaryPermissionInsideSecuredFolder(parentFolder, orgMember, CRUD.READ, securedFolderPermissionService, publicFolderService);

        // 1. Retrieve all subfolders in the given folder in the organization space

        List<FileOrFolderDto> filesAndFolders = folderRepository.findAllBySpaceAndParentFolderOrderByName(Workspace.ORG_SPACE, parentFolder)
                .stream()
                .map(folder -> {
                    FolderDto folderDto = FolderDto.fromFolderAndOrgMember(folder, folder.getCreatedBy());
                    folderDto.setSecurity(folder.getPublicFolder().getSecurity());
                    folderDto.setType(FileOrFolder.FOLDER);
                    return folderDto;
                }).collect(Collectors.toList());

        // 2. Retrieve all files in the given folder in the organization space

        filesAndFolders.addAll(fileService.findAllByFolderAndOrgMember(parentFolder, orgMember));

        return filesAndFolders;
    }
}
