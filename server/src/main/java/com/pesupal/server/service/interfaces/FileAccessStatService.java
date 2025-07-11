package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.FileAccessStatDto;
import com.pesupal.server.dto.response.FileDto;

import java.util.List;

public interface FileAccessStatService {

    List<FileAccessStatDto> getFileAccessStats(Long fileId, Long userId, Long orgId);

    List<FileDto> getRecentlyAccessedFiles(Long userId, Long orgId);
}
