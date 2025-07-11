package com.pesupal.server.strategies.workspace;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.CRUD;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.WorkspaceSupportsPublicFolder;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.PublicFolder;
import com.pesupal.server.model.workdrive.TeamFolder;
import com.pesupal.server.repository.FolderRepository;
import com.pesupal.server.repository.PublicFolderRepository;
import com.pesupal.server.repository.TeamFolderRepository;
import com.pesupal.server.service.interfaces.FileService;
import com.pesupal.server.service.interfaces.PublicFolderService;
import com.pesupal.server.service.interfaces.SecuredFolderPermissionService;
import com.pesupal.server.service.interfaces.WorkdriveSpace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("TEAM_SPACE")
@AllArgsConstructor
public class TeamSpace extends WorkspaceSupportsPublicFolder implements WorkdriveSpace {

    private final FileService fileService;
    private final FolderRepository folderRepository;
    private final PublicFolderService publicFolderService;
    private final TeamFolderRepository teamFolderRepository;
    private final PublicFolderRepository publicFolderRepository;
    private final SecuredFolderPermissionService securedFolderPermissionService;

    /**
     * Saves a folder in the team space and associates it with the public folder and team folder.
     *
     * @param folder
     * @return Folder
     */
    @Override
    public Folder save(Folder folder, CreateFolderDto createFolderDto, OrgMember orgMember) {

        ensureNecessaryPermissionInsideSecuredFolder(folder.getParentFolder(), orgMember, CRUD.CREATE, securedFolderPermissionService, publicFolderService);

        folder = folderRepository.save(folder);
        PublicFolder publicFolder = publicFolderRepository.save(getPublicFolder(folder, createFolderDto));
        Department department = orgMember.getDepartment();
        teamFolderRepository.save(new TeamFolder(folder, department, publicFolder));
        return folder;
    }

    /**
     * Finds all folders by the organization member in the team space.
     *
     * @param orgMember
     * @param parentFolder
     * @return List of FolderDto
     */
    @Override
    public List<FileOrFolderDto> findAllFilesAndFoldersByOrgMemberAndFolder(OrgMember orgMember, Folder parentFolder) {

        ensureNecessaryPermissionInsideSecuredFolder(parentFolder, orgMember, CRUD.READ, securedFolderPermissionService, publicFolderService);

        Department department = orgMember.getDepartment();

        if (parentFolder != null) {    // Not a root folder
            TeamFolder teamFolder = teamFolderRepository.findByFolder(parentFolder).orElseThrow(() -> new DataNotFoundException("Folder '" + parentFolder.getName() + "' not found in team space."));
            if (!teamFolder.getDepartment().getId().equals(department.getId())) {
                throw new PermissionDeniedException("You don't have permission to access other team's folders.");
            }
        }

        // 1. Retrieve all subfolders in the given folder in the team space

        List<FileOrFolderDto> filesAndFolders = teamFolderRepository.findByDepartmentAndFolder_ParentFolder(department, parentFolder)
                .stream()
                .map(teamFolder -> {
                    Folder folder = teamFolder.getFolder();
                    FolderDto folderDto = FolderDto.fromFolderAndOrgMember(folder, orgMember);
                    folderDto.setType(FileOrFolder.FOLDER);
                    folderDto.setSecurity(folder.getPublicFolder().getSecurity());
                    return folderDto;
                }).collect(Collectors.toList());

        // 2. Retrieve all files in the given folder in the team space

        filesAndFolders.addAll(fileService.findAllByFolderAndOrgMember(parentFolder, orgMember));

        return filesAndFolders;
    }
}
