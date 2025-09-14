package com.pesupal.server.repository.drive;

import com.pesupal.server.enums.Workspace;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findByIdAndCreator_OrgId(Long id, Long orgId);

    Optional<File> findByPublicId(String publicId);

    List<File> findAllByFolderAndDeleted(Folder parentFolder, boolean deleted, Sort sort);

    List<File> findAllByCreator_Org(Org creatorOrg);

    List<File> findAllByCreator_OrgAndFolder_Space(Org org, Workspace workspace);

    List<File> findAllByDeleted(boolean deleted);
}
