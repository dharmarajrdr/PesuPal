package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.FileAccessStatDto;

import java.util.List;

public interface FileAccessStatService {

    List<FileAccessStatDto> getFileAccessStats(Long fileId, Long currentUserId, Long currentOrgId);
}
