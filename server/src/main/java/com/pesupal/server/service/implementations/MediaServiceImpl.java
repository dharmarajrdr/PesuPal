package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.enums.Extension;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.service.interfaces.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
     * @return Resource
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
     * Ensures that the file size does not exceed the maximum allowed size.
     *
     * @param file
     * @throws IOException
     */
    private void ensureFileSize(MultipartFile file) {
        if (file.getSize() > StaticConfig.MAX_FILE_SIZE_IN_MB * 1024 * 1024) {
            throw new ActionProhibitedException("File size exceeds the maximum limit of " + StaticConfig.MAX_FILE_SIZE_IN_MB + "MB");
        }
    }

    /**
     * Saves a media file and returns its unique identifier.
     *
     * @param file
     * @return UUID
     */
    @Override
    public UUID saveMedia(MultipartFile file) throws IOException {

        ensureFileSize(file);
        UUID mediaId = UUID.randomUUID();
        String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf('.'));
        Path uploadPath = Paths.get(StaticConfig.MEDIA_PATH);
        Path filePath = uploadPath.resolve(mediaId + extension);
        Files.write(filePath, file.getBytes());
        return mediaId;
    }

    /**
     * Retrieves the file size in kilobytes for a given media ID.
     *
     * @param mediaId
     * @return Long
     */
    @Override
    public Long getFileSizeInKB(UUID mediaId) {

        String partialName = StaticConfig.MEDIA_PATH + "/" + mediaId.toString();

        try {
            // Build the shell command
            String[] cmd = {
                    "bash", "-c",
                    "du -k -- " + partialName + ".* 2>/dev/null | awk '{print $1}'"
            };

            // Execute command
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();

            // Parse result
            if (line != null && !line.isEmpty()) {
                return Long.parseLong(line.trim());
            }
        } catch (Exception e) {
        }
        return null; // Return 0 if an error occurs or no size is found
    }
}
