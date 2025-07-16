package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.MediaUploadDto;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    public MediaUploadDto uploadFile(MultipartFile file) throws Exception;

    public byte[] downloadFile(String key);
}
