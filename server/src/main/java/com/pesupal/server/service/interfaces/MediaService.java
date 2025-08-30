package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.MediaUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.UUID;

public interface MediaService {

    MediaUploadDto uploadFile(MultipartFile file) throws Exception;

    byte[] downloadFile(String key);

    URL generatePresignedUrl(UUID key);

    void deleteFile(UUID key);
}
