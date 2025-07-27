package com.pesupal.server.repository;

import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;
import com.pesupal.server.model.workdrive.StarredFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarredFileRepository extends JpaRepository<StarredFile, Long> {

    boolean existsByFileAndStarredBy(File file, OrgMember orgMember);
}
