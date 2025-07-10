package com.pesupal.server.repository;

import com.pesupal.server.model.workdrive.TeamFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamFolderRepository extends JpaRepository<TeamFolder, Long> {
}
