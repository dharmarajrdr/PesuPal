package com.pesupal.server.strategies.workspace;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.enums.Security;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.FolderRepository;
import com.pesupal.server.service.interfaces.FileService;
import com.pesupal.server.service.interfaces.WorkdriveSpace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("PERSONAL_SPACE")
@AllArgsConstructor
public class PersonalSpace implements WorkdriveSpace {

    private final FileService fileService;
    private final FolderRepository folderRepository;

    /**
     * Saves a folder in the personal space.
     *
     * @param folder
     * @return Folder
     */
    @Override
    public Folder save(Folder folder, CreateFolderDto createFolderDto, OrgMember orgMember) {

        return folderRepository.save(folder);
    }

    /**
     * Finds all folders by the organization member in the personal space.
     *
     * @param orgMember
     * @return
     */
    @Override
    public List<FileOrFolderDto> findAllFilesAndFoldersByOrgMemberAndFolder(OrgMember orgMember, Folder parentFolder) {

        // 1. Retrieve all subfolders in the given folder in the personal space

        List<FileOrFolderDto> filesAndFolders = folderRepository.findAllByCreatedByAndSpaceAndParentFolder(orgMember, Workspace.PERSONAL_SPACE, parentFolder)
                .stream()
                .map(folder -> {
                    FolderDto folderDto = FolderDto.fromFolderAndOrgMember(folder, orgMember);
                    folderDto.setSecurity(Security.SECURED);    // Personal space folders are always secured
                    folderDto.setType(FileOrFolder.FOLDER);
                    return folderDto;
                }).collect(Collectors.toList());

        // 2. Retrieve all files in the given folder in the personal space

        filesAndFolders.addAll(fileService.findAllByFolderAndOrgMember(parentFolder, orgMember));

        return filesAndFolders;
    }
}
