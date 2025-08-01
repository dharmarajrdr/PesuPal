package com.pesupal.server.repository;

import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByFolder(Folder folder);

    Optional<File> findByIdAndCreator_OrgId(Long id, Long orgId);
}
