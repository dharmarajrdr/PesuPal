package com.pesupal.server.repository.drive;

import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.Folder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    
    Optional<Folder> findByPublicId(String folderPublicId);

    List<Folder> findAllBySpaceAndParentFolderAndDeleted(Workspace workspace, Folder parentFolder, boolean deleted, Sort sort);

    List<Folder> findAllByCreatedByAndSpaceAndParentFolderAndDeleted(OrgMember orgMember, Workspace workspace, Folder parentFolder, boolean deleted, Sort sort);

    boolean existsByNameAndSpaceAndParentFolder_PublicIdAndDeleted(String name, Workspace space, String parentFolderId, boolean deleted);
}
