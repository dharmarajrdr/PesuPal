package com.pesupal.server.dto.request;

import com.pesupal.server.enums.Security;
import com.pesupal.server.model.workdrive.File;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateFileDto {

    private String name;

    private Long folderId;

    private UUID mediaId;

    private Security security;

    public File toFile() {
        
        File file = new File();
        file.setName(name);
        file.setMediaId(mediaId);
        file.setSecurity(security);
        return file;
    }
}
