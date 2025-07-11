package com.pesupal.server.service.interfaces;

import com.pesupal.server.enums.CRUD;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.PublicFolder;
import com.pesupal.server.model.workdrive.SecuredFolderPermission;

import java.util.Optional;

public interface SecuredFolderPermissionService {

    Optional<SecuredFolderPermission> getSecuredFolderPermissionByFolderAndUser(Folder folder, User user);

    boolean hasNecessaryPermission(PublicFolder parentPublicFolder, OrgMember orgMember, CRUD crud);

    boolean hasWritePermission(PublicFolder parentPublicFolder, OrgMember orgMember);

    boolean hasReadPermission(PublicFolder parentPublicFolder, OrgMember orgMember);
}
