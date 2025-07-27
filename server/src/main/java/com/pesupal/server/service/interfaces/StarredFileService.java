package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.workdrive.File;

import java.util.List;

public interface StarredFileService {

    boolean existsByFileAndStarredBy(File file, OrgMember starredBy);

    void addStarredFile(Long fileId);

    List<FileDto> getStarredFiles(Long userId, Long orgId);
}
