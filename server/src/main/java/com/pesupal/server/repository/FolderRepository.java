package com.pesupal.server.repository;

import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

    boolean existsByNameAndSpaceAndParentFolder_PublicId(String name, Workspace space, String parentFolderId);

    Optional<Folder> findByPublicId(String folderPublicId);

    List<Folder> findAllByCreatedByAndSpaceAndParentFolder(OrgMember createdBy, Workspace space, Folder parentFolder);

    List<Folder> findAllBySpaceAndParentFolder(Workspace workspace, Folder parentFolder);
}
