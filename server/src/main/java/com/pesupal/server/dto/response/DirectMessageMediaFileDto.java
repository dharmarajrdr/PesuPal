package com.pesupal.server.dto.response;

import com.pesupal.server.model.chat.DirectMessageMediaFile;
import lombok.Data;

import java.net.URL;

@Data
public class DirectMessageMediaFileDto {

    private Long id;

    private String extension;

    private URL mediaUrl;

    public static DirectMessageMediaFileDto fromDirectMessageMediaFile(DirectMessageMediaFile directMessageMediaFile) {
        DirectMessageMediaFileDto dto = new DirectMessageMediaFileDto();
        dto.setId(directMessageMediaFile.getId());
        dto.setExtension(directMessageMediaFile.getExtension());
        return dto;
    }
}
