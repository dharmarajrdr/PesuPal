package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.FileDto;
import com.pesupal.server.model.user.User;
import com.pesupal.server.model.workdrive.File;

import java.util.List;

public interface StarredFileService {

    boolean existsByFileAndUser(File file, User user);

    void addStarredFile(Long fileId, Long userId, Long orgId);

    List<FileDto> getStarredFiles(Long userId, Long orgId);
}
