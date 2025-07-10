package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.enums.Extension;
import com.pesupal.server.service.interfaces.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements MediaService {

    /**
     * Retrieves a media resource by its unique identifier.
     *
     * @param mediaId
     * @return
     */
    @Override
    public Resource getResourceById(UUID mediaId, String contentType) throws MalformedURLException, FileNotFoundException {

        String fileName = mediaId.toString() + Extension.fromContentType(contentType).getExtension();
        File file = new File(StaticConfig.MEDIA_PATH + File.separator + fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("Media file not found: " + fileName);
        }
        Path path = file.toPath();
        return new UrlResource(path.toUri());
    }

    /**
     * Saves a media file and returns its unique identifier.
     *
     * @param file
     * @return
     */
    @Override
    public UUID saveMedia(MultipartFile file) throws IOException {

        UUID mediaId = UUID.randomUUID();
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.'));
        Path uploadPath = Paths.get(StaticConfig.MEDIA_PATH);
        Path filePath = uploadPath.resolve(mediaId.toString() + extension);
        Files.write(filePath, file.getBytes());
        return mediaId;
    }
}
