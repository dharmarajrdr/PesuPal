package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.dto.response.FileOrFolderDto;
import com.pesupal.server.dto.response.FolderDto;
import com.pesupal.server.enums.Arithmetic;
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
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImpl implements FolderService {

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
     * @param userId
     * @param orgId
     * @return FolderDto
     */
    @Override
    @Transactional
    public FolderDto createFolder(CreateFolderDto createFolderDto, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);

        if (folderRepository.existsByNameAndSpaceAndParentFolderId(createFolderDto.getName(), createFolderDto.getSpace(), createFolderDto.getParentFolderId())) {
            throw new ActionProhibitedException("A folder with the name '" + createFolderDto.getName() + "' already exists.");
        }

        Folder folder = createFolderDto.toFolder();
        folder.setOrg(orgMember.getOrg());
        folder.setSize(0L); // Initial size of folder
        folder.setOwner(orgMember.getUser());
        if (createFolderDto.getParentFolderId() != null) {
            Folder parentFolder = getFolderByIdAndOrgId(createFolderDto.getParentFolderId(), orgId);
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
     * @param orgId
     * @return Folder
     */
    @Override
    public Folder getFolderByIdAndOrgId(Long folderId, Long orgId) {

        return folderRepository.findByIdAndOrgId(folderId, orgId).orElseThrow(() -> new DataNotFoundException("Folder with ID " + folderId + " not found in this org."));
    }

    /**
     * Retrieves all folders under a specific parent folder for a user in an organization.
     *
     * @param folderId
     * @param userId
     * @param orgId
     * @return List of FolderDto
     */
    @Override
    public List<FileOrFolderDto> getAllFolders(Long folderId, Long userId, Long orgId) {

        Folder parentFolder = getFolderByIdAndOrgId(folderId, orgId);
        Workspace workspace = parentFolder.getSpace();
        WorkdriveSpace workdriveSpace = workspaceFactory.getFactory(workspace);
        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        return workdriveSpace.findAllFilesAndFoldersByOrgMemberAndFolder(orgMember, parentFolder);
    }

    /**
     * Retrieves all folders in the specified workspace for a user.
     *
     * @param space
     * @param userId
     * @param orgId
     * @return List of FolderDto
     */
    @Override
    public List<FileOrFolderDto> getAllFolders(Workspace space, Long userId, Long orgId) {

        WorkdriveSpace workdriveSpace = workspaceFactory.getFactory(space);
        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        return workdriveSpace.findAllFilesAndFoldersByOrgMemberAndFolder(orgMember, null);  // `null` indicates root folder
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

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        Folder folder = getFolderById(folderId);

        if (!folder.getOwner().getId().equals(orgMember.getUser().getId())) {
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
