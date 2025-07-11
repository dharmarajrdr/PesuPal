package com.pesupal.server.strategies.workspace;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.FileOrFolder;
import com.pesupal.server.enums.Security;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.FileRepository;
import com.pesupal.server.repository.FolderRepository;
import com.pesupal.server.service.interfaces.WorkdriveSpace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("PERSONAL_SPACE")
@AllArgsConstructor
public class PersonalSpace implements WorkdriveSpace {

    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;

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
    public List<FileOrFolderDto> findAllFilesAndFoldersByOrgMember(OrgMember orgMember, Folder parentFolder) {

        List<FileOrFolderDto> filesAndFolders = new ArrayList<>();

        // 1. Retrieve all subfolders in the given folder in the personal space

        List<Folder> subFolders = folderRepository.findAllByOrgAndOwnerAndSpaceAndParentFolder(orgMember.getOrg(), orgMember.getUser(), Workspace.PERSONAL_SPACE, parentFolder);

        for (Folder subFolder : subFolders) {
            FolderDto folderDto = FolderDto.fromFolderAndOrgMember(subFolder, orgMember);
            folderDto.setSecurity(Security.SECURED);    // Personal space folders are always secured
            folderDto.setType(FileOrFolder.FOLDER);
            filesAndFolders.add(folderDto);
        }

        // 2. Retrieve all files in the given folder in the personal space

        List<File> files = fileRepository.findAllByFolder(parentFolder);

        for (File file : files) {
            FileDto fileDto = FileDto.fromFileAndOrgMember(file, orgMember);
            fileDto.setType(FileOrFolder.FILE);
            filesAndFolders.add(fileDto);
        }

        return filesAndFolders;
    }
}
