package com.pesupal.server.dto.request.drive;

import com.pesupal.server.enums.Security;
import com.pesupal.server.model.workdrive.File;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateFileDto {

    private String name;

    private String folderId;

    private UUID mediaId;

    private Long size;

    private String extension;

    private Security security;

    public File toFile() {

        File file = new File();
        file.setName(name);
        file.setMediaId(mediaId);
        file.setExtension(extension);
        file.setSize(size);
        file.setSecurity(security);
        return file;
    }
}
