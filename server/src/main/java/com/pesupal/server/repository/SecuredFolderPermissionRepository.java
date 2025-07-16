package com.pesupal.server.repository;

import com.pesupal.server.model.user.User;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.SecuredFolderPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecuredFolderPermissionRepository extends JpaRepository<SecuredFolderPermission, Long> {

    Optional<SecuredFolderPermission> findByFolderAndUser(Folder folder, User user);
}
