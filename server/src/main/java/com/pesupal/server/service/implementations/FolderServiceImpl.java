package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.factory.WorkspaceFactory;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.FolderRepository;
import com.pesupal.server.service.interfaces.FolderService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.WorkdriveSpace;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final OrgMemberService orgMemberService;
    private final FolderRepository folderRepository;
    private final WorkspaceFactory workspaceFactory;

    /**
     * Creates a new folder in the specified workspace.
     *
     * @param createFolderDto
     * @param userId
     * @param orgId
     * @return FolderDto
     */
    @Override
    public FolderDto createFolder(CreateFolderDto createFolderDto, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);

        if (folderRepository.existsByNameAndSpaceAndParentFolderId(createFolderDto.getName(), createFolderDto.getSpace(), createFolderDto.getParentFolderId())) {
            throw new ActionProhibitedException("A folder with the name '" + createFolderDto.getName() + "' already exists.");
        }

        Folder folder = createFolderDto.toFolder();
        folder.setOrg(orgMember.getOrg());
        folder.setOwner(orgMember.getUser());
        if (createFolderDto.getParentFolderId() != null) {
            folder.setParentFolder(getFolderById(createFolderDto.getParentFolderId()));
        }
        WorkdriveSpace workdriveSpace = workspaceFactory.getFactory(createFolderDto.getSpace());
        folder = workdriveSpace.save(folder, createFolderDto, orgMember);
        FolderDto folderDto = FolderDto.fromFolderAndOrgMember(folder, orgMember);
        folderDto.setSecurity(createFolderDto.getSecurity());
        return folderDto;
    }

    /**
     * Retrieves a folder by its ID.
     *
     * @param folderId
     * @return
     */
    @Override
    public Folder getFolderById(Long folderId) {

        return folderRepository.findById(folderId).orElseThrow(() -> new DataNotFoundException("Folder with ID " + folderId + " not found."));
    }

    /**
     * Retrieves all folders in the specified workspace for a user.
     *
     * @param space
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<FolderDto> getAllFolders(Workspace space, Long userId, Long orgId) {
        return List.of();
    }

    /**
     * Retrieves a folder by its ID in the specified workspace for a user.
     *
     * @param folderId
     * @param userId
     * @param orgId
     */
    @Override
    public void deleteFolder(Long folderId, Long userId, Long orgId) {

    }
}
