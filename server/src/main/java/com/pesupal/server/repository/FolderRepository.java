package com.pesupal.server.repository;

import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.workdrive.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    boolean existsByNameAndSpaceAndParentFolderId(String name, Workspace space, Long parentFolderId);
}
