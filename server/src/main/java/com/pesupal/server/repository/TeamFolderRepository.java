package com.pesupal.server.repository;

import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.workdrive.Folder;
import com.pesupal.server.model.workdrive.TeamFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamFolderRepository extends JpaRepository<TeamFolder, Long> {

    Optional<TeamFolder> findByFolder(Folder folder);

    List<TeamFolder> findByDepartmentAndFolder_ParentFolder(Department department, Folder parentFolder);

}
