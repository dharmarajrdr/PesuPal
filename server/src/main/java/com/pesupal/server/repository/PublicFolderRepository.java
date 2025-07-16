package com.pesupal.server.repository;

import com.pesupal.server.model.workdrive.PublicFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicFolderRepository extends JpaRepository<PublicFolder, Long> {
}
