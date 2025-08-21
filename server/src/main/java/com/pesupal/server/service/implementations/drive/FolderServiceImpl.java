package com.pesupal.server.service.implementations.drive;

import com.pesupal.server.dto.request.drive.CreateFolderDto;
import com.pesupal.server.dto.response.drive.FileOrFolderDto;
import com.pesupal.server.dto.response.drive.FolderDto;
import com.pesupal.server.dto.response.drive.FolderPreviewDto;
import com.pesupal.server.enums.Arithmetic;
import com.pesupal.server.enums.Workspace;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.factory.WorkspaceFactory;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.repository.drive.FileRepository;
import com.pesupal.server.repository.drive.FolderRepository;
import com.pesupal.server.service.interfaces.drive.FolderService;
import com.pesupal.server.service.interfaces.drive.WorkdriveSpace;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderServiceImpl extends CurrentValueRetriever implements FolderService {

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final WorkspaceFactory workspaceFactory;

    public FolderServiceImpl(FolderRepository folderRepository, @Lazy WorkspaceFactory workspaceFactory, FileRepository fileRepository) {
        this.fileRepository = fileRepository;
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

        if (folderRepository.existsByNameAndSpaceAndParentFolder_PublicIdAndDeleted(createFolderDto.getName(), createFolderDto.getSpace(), createFolderDto.getParentFolderId(), false)) {
            throw new ActionProhibitedException("A folder with the name '" + createFolderDto.getName() + "' already exists.");
        }

        Folder folder = createFolderDto.toFolder();
        folder.setSize(0L); // Initial size of folder
        folder.setCreatedBy(orgMember);
        if (createFolderDto.getParentFolderId() != null) {
            Folder parentFolder = getFolderByPublicId(createFolderDto.getParentFolderId());
            if (parentFolder != null) {
                if (!parentFolder.getSpace().equals(createFolderDto.getSpace())) {
                    throw new IllegalArgumentException("Folder '" + parentFolder.getName() + "' does not belong to " + createFolderDto.getSpace().getValue() + " space.");
                }
                if (parentFolder.isDeleted()) {
                    throw new ActionProhibitedException("Unable to create folder under deleted folder.");
                }
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
        if (parentFolder.isDeleted()) {
            throw new DataNotFoundException("Folder not found or has been deleted.");
        }
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
    public void deleteFolder(String folderId) {

        OrgMember orgMember = getCurrentOrgMember();
        Folder folder = getFolderByPublicId(folderId);

        if (!folder.getCreatedBy().getId().equals(orgMember.getId())) {
            throw new ActionProhibitedException("You do not have permission to delete this folder.");
        }

        if (folder.isDeleted()) {
            throw new ActionProhibitedException("This folder is already deleted.");
        }

        folder.setDeleted(true);
        folderRepository.save(folder);

        deleteSubFilesAndFolders(folder);
    }

    /**
     * Recursively deletes all files and subfolders within a folder.
     *
     * @param folder
     */
    private void deleteSubFilesAndFolders(Folder folder) {

        for (File file : folder.getFiles()) {
            file.setDeleted(true);
            fileRepository.save(file);
        }
        for (Folder subFolder : folder.getSubFolders()) {
            subFolder.setDeleted(true);
            folderRepository.save(subFolder);
            deleteSubFilesAndFolders(subFolder);
        }
    }

    /**
     * Restores a deleted folder by its ID.
     *
     * @param folderId
     */
    @Override
    public void restoreFolder(String folderId) {

        OrgMember orgMember = getCurrentOrgMember();
        Folder folder = getFolderByPublicId(folderId);

        if (!folder.getCreatedBy().getId().equals(orgMember.getId())) {
            throw new ActionProhibitedException("You do not have permission to restore this folder.");
        }

        if (!folder.isDeleted()) {
            throw new ActionProhibitedException("This folder is not deleted and cannot be restored.");
        }

        folder.setDeleted(false);
        folderRepository.save(folder);
    }

    /**
     * Clears the contents of a folder by its ID.
     *
     * @param folderId
     */
    @Override
    public void clearFolder(String folderId) {

        Folder folder = getFolderByPublicId(folderId);
        OrgMember orgMember = getCurrentOrgMember();

        if (!folder.getCreatedBy().getId().equals(orgMember.getId())) {
            throw new ActionProhibitedException("You do not have permission to clear this folder.");
        }

        deleteSubFilesAndFolders(folder);
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

    /**
     * Retrieves the parent folders of a given folder by its ID.
     *
     * @param folderId
     * @return
     */
    @Override
    public List<FolderPreviewDto> getParentFolders(String folderId) {

        if (folderId == null) {
            return List.of();
        }

        Folder folder = getFolderByPublicId(folderId);
        Workspace space = folder.getSpace();

        List<FolderPreviewDto> folderPreviewDtos = new ArrayList<>();
        while (folder != null) {
            FolderPreviewDto folderPreviewDto = FolderPreviewDto.fromFolder(folder);
            folderPreviewDtos.add(0, folderPreviewDto); // Add to the beginning to maintain order
            folder = folder.getParentFolder();
        }
        folderPreviewDtos.add(0, FolderPreviewDto.builder().name(space.getDisplayName()).id(null).space(space).build());
        return folderPreviewDtos;
    }
}
