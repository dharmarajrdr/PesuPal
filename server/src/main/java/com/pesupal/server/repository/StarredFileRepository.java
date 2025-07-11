package com.pesupal.server.repository;

import com.pesupal.server.model.user.User;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.StarredFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarredFileRepository extends JpaRepository<StarredFile, Long> {

    boolean existsByFileAndUser(File file, User user);
}
