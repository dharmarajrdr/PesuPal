package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.Arithmetic;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.factory.WorkspaceFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.FolderRepository;
import com.pesupal.server.service.interfaces.FolderService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.WorkdriveSpace;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImpl extends CurrentValueRetriever implements FolderService {

    private final OrgMemberService orgMemberService;
    private final FolderRepository folderRepository;
    private final WorkspaceFactory workspaceFactory;

    public FolderServiceImpl(OrgMemberService orgMemberService, FolderRepository folderRepository, @Lazy WorkspaceFactory workspaceFactory) {
        this.orgMemberService = orgMemberService;
        this.folderRepository = folderRepository;
        this.workspaceFactory = workspaceFactory;
    }

    /**
     * Creates a new folder in the specified workspace.
     *
     * @param createFolderDto
     * @return FolderDto
     */
    @Override
    @Transactional
    public FolderDto createFolder(CreateFolderDto createFolderDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();

        if (folderRepository.existsByNameAndSpaceAndParentFolder_PublicId(createFolderDto.getName(), createFolderDto.getSpace(), createFolderDto.getParentFolderId())) {
            throw new ActionProhibitedException("A folder with the name '" + createFolderDto.getName() + "' already exists.");
        }

        Folder folder = createFolderDto.toFolder();
        folder.setSize(0L); // Initial size of folder
        folder.setCreatedBy(orgMember);
        if (createFolderDto.getParentFolderId() != null) {
            Folder parentFolder = getFolderByPublicId(createFolderDto.getParentFolderId());
            if (parentFolder != null && !parentFolder.getSpace().equals(createFolderDto.getSpace())) {
                throw new IllegalArgumentException("Folder '" + parentFolder.getName() + "' does not belong to " + createFolderDto.getSpace().getValue() + " space.");
            }
            folder.setParentFolder(parentFolder);
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
     * @return Folder
     */
    @Override
    public Folder getFolderById(Long folderId) {

        return folderRepository.findById(folderId).orElseThrow(() -> new DataNotFoundException("Folder with ID " + folderId + " not found."));
    }

    /**
     * Retrieves a folder by its ID and organization ID.
     *
     * @param folderId
     * @return Folder
     */
    @Override
    public Folder getFolderByPublicId(String folderId) {

        return folderRepository.findByPublicId(folderId).orElseThrow(() -> new DataNotFoundException("Folder with ID " + folderId + " not found in this org."));
    }

    /**
     * Retrieves all folders under a specific parent folder for a user in an organization.
     *
     * @param folderId
     * @return List of FolderDto
     */
    @Override
    public List<FileOrFolderDto> getAllFolders(String folderId) {

        OrgMember orgMember = getCurrentOrgMember();
        Folder parentFolder = getFolderByPublicId(folderId);
        Workspace workspace = parentFolder.getSpace();
        WorkdriveSpace workdriveSpace = workspaceFactory.getFactory(workspace);
        return workdriveSpace.findAllFilesAndFoldersByOrgMemberAndFolder(orgMember, parentFolder);
    }

    /**
     * Retrieves all folders in the specified workspace for a user.
     *
     * @param space
     * @return List of FolderDto
     */
    @Override
    public List<FileOrFolderDto> getAllFolders(Workspace space) {

        OrgMember orgMember = getCurrentOrgMember();
        WorkdriveSpace workdriveSpace = workspaceFactory.getFactory(space);
        return workdriveSpace.findAllFilesAndFoldersByOrgMemberAndFolder(orgMember, null);  // `null` indicates root folder
    }

    /**
     * Retrieves a folder by its ID in the specified workspace for a user.
     *
     * @param folderId
     */
    @Override
    public void deleteFolder(Long folderId) {

        OrgMember orgMember = getCurrentOrgMember();
        Folder folder = getFolderById(folderId);

        if (!folder.getCreatedBy().getId().equals(orgMember.getId())) {
            throw new ActionProhibitedException("You do not have permission to delete this folder.");
        }

        folderRepository.delete(folder);
    }

    /**
     * Updates the size of a folder recursively, adjusting its size based on the specified arithmetic operation.
     *
     * @param folder
     * @param size
     * @param arithmetic
     */
    @Override
    public void updateFolderSizeRecursively(Folder folder, Long size, Arithmetic arithmetic) {

        if (folder == null) {
            return;     // root folder reached
        }

        /**
         * Prevents infinite recursion if the folder's parent is itself.
         * This can happen if the folder is incorrectly set to be its own parent.
         */
        if (folder.getParentFolder() != null && folder.getParentFolder().getId().equals(folder.getId())) {
            return;
        }

        Long currentSize = folder.getSize();
        if (currentSize == null) {
            currentSize = 0L; // Initialize size if it's null
        }
        folder.setSize(currentSize + (arithmetic == Arithmetic.PLUS ? size : -size));
        folderRepository.save(folder);
        updateFolderSizeRecursively(folder.getParentFolder(), size, arithmetic);
    }
}
