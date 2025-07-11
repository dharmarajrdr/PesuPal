package com.pesupal.server.service.implementations;

import com.pesupal.server.enums.CRUD;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.PublicFolder;
import com.pesupal.server.model.workdrive.SecuredFolderPermission;
import com.pesupal.server.repository.SecuredFolderPermissionRepository;
import com.pesupal.server.service.interfaces.SecuredFolderPermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SecuredFolderPermissionServiceImpl implements SecuredFolderPermissionService {

    private final SecuredFolderPermissionRepository securedFolderPermissionRepository;

    /**
     * Retrieves the SecuredFolderPermission for a given folder and user.
     *
     * @param folder
     * @param user
     * @return SecuredFolderPermission
     */
    @Override
    public Optional<SecuredFolderPermission> getSecuredFolderPermissionByFolderAndUser(Folder folder, User user) {

        return securedFolderPermissionRepository.findByFolderAndUser(folder, user);
    }

    /**
     * Checks if the user has the necessary permission for a CRUD operation in a secured public folder.
     *
     * @param parentPublicFolder
     * @param orgMember
     * @param crud
     * @return boolean
     */
    @Override
    public boolean hasNecessaryPermission(PublicFolder parentPublicFolder, OrgMember orgMember, CRUD crud) {

        SecuredFolderPermission securedFolderPermission = getSecuredFolderPermissionByFolderAndUser(parentPublicFolder.getFolder(), orgMember.getUser())
                .orElseThrow(() -> new DataNotFoundException("User does not have " + crud.name().toLowerCase() + " access on this folder."));
        return switch (crud) {
            case CREATE, UPDATE, DELETE -> securedFolderPermission.isWritable();
            case READ -> securedFolderPermission.isReadable();
        };
    }

    /**
     * Checks if the user has write permission in a secured public folder.
     *
     * @param parentPublicFolder
     * @param orgMember
     * @return boolean
     */
    @Override
    public boolean hasWritePermission(PublicFolder parentPublicFolder, OrgMember orgMember) {

        return getSecuredFolderPermissionByFolderAndUser(parentPublicFolder.getFolder(), orgMember.getUser())
                .orElseThrow(() -> new DataNotFoundException("User does not have write access on this folder."))
                .isWritable();
    }

    /**
     * Checks if the user has read permission in a secured public folder.
     *
     * @param parentPublicFolder
     * @param orgMember
     * @return boolean
     */
    @Override
    public boolean hasReadPermission(PublicFolder parentPublicFolder, OrgMember orgMember) {

        return getSecuredFolderPermissionByFolderAndUser(parentPublicFolder.getFolder(), orgMember.getUser())
                .orElseThrow(() -> new DataNotFoundException("User does not have read access in this folder."))
                .isReadable();
    }
}
