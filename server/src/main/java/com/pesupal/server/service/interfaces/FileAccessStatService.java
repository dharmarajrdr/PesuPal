package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.FileAccessStatDto;
import com.pesupal.server.dto.response.FileDto;

import java.util.List;

public interface FileAccessStatService {

    List<FileAccessStatDto> getFileAccessStats(Long fileId);

    List<FileDto> getRecentlyAccessedFiles();
}
