package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.MediaUploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface MediaService {

    MediaUploadDto uploadFile(MultipartFile file) throws Exception;

    byte[] downloadFile(String key);

    URL generatePresignedUrl(String key) throws Exception;
}
