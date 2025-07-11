package com.pesupal.server.repository;

import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import com.pesupal.server.model.workdrive.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    boolean existsByNameAndSpaceAndParentFolderId(String name, Workspace space, Long parentFolderId);

    Optional<Folder> findByIdAndOrgId(Long folderId, Long orgId);

    List<Folder> findAllByOrgAndOwnerAndSpaceAndParentFolder(Org org, User owner, Workspace space, Folder parentFolder);
}
