package com.pesupal.server.helpers;

import com.pesupal.server.dto.request.CreateFolderDto;
import com.pesupal.server.enums.CRUD;
import com.pesupal.server.enums.Security;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.PublicFolder;
import com.pesupal.server.service.interfaces.SecuredFolderPermissionService;

public abstract class WorkspaceSupportsPublicFolder {

    /**
     * Ensures that the user has permission to create a folder in a secured parent folder.
     *
     * @param folder
     * @param orgMember
     */
    protected void ensureNecessaryPermissionInsideSecuredFolder(Folder folder, OrgMember orgMember, CRUD crud, SecuredFolderPermissionService securedFolderPermissionService) {

        Folder parentFolder = folder.getParentFolder();

        if (parentFolder == null) {
            return; // No parent folder, no need to check permissions
        }

        PublicFolder parentPublicFolder = parentFolder.getPublicFolder();

        boolean isParentFolderSecured = parentPublicFolder != null && parentPublicFolder.getSecurity().equals(Security.SECURED);
        if (isParentFolderSecured) {    // If the parent folder is secured

            boolean isOwnerOfParentFolder = orgMember.getUser().getId().equals(parentFolder.getOwner().getId());
            if (!isOwnerOfParentFolder) {   // If the user is not the owner of the parent folder

                boolean hasNecessaryPermission = securedFolderPermissionService.hasNecessaryPermission(parentPublicFolder, orgMember, crud);
                if (!hasNecessaryPermission) {  // If the user does not have write permission in the secured parent folder

                    throw new PermissionDeniedException("You do not have permission to " + crud.name().toLowerCase() + " this folder. Please request access from the owner.");
                }
            }
        }
    }

    protected PublicFolder getPublicFolder(Folder folder, CreateFolderDto createFolderDto) {

        PublicFolder publicFolder = new PublicFolder();
        publicFolder.setFolder(folder);
        publicFolder.setSecurity(createFolderDto.getSecurity());
        Security security = createFolderDto.getSecurity();
        applySecuritySettings(publicFolder, security);
        return publicFolder;
    }

    /**
     * Default security settings for public folders.
     *
     * @param publicFolder
     * @param security
     */
    protected void applySecuritySettings(PublicFolder publicFolder, Security security) {
        switch (security) {
            case NONE: {
                publicFolder.setReadable(true);
                publicFolder.setWritable(true);
                publicFolder.setDeletable(false);
                break;
            }
            case SECURED: {
                publicFolder.setReadable(false);
                publicFolder.setWritable(false);
                publicFolder.setDeletable(false);
                break;
            }
        }
    }
}
