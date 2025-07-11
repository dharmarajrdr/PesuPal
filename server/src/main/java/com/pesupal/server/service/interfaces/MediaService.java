package com.pesupal.server.service.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

public interface MediaService {

    Resource getResourceById(UUID mediaId, String contentType) throws MalformedURLException, FileNotFoundException;

    UUID saveMedia(MultipartFile file) throws IOException;

    Long getFileSizeInKB(UUID mediaId) throws Exception;
}
