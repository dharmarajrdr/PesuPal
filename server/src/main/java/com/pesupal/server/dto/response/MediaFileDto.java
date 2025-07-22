package com.pesupal.server.dto.response;

import com.pesupal.server.model.chat.DirectMessageMediaFile;
import com.pesupal.server.model.group.GroupMessageMediaFile;
import lombok.Data;

import java.net.URL;

@Data
public class MediaFileDto {

    private Long id;

    private String extension;

    private URL mediaUrl;

    public static MediaFileDto fromDirectMessageMediaFile(DirectMessageMediaFile directMessageMediaFile) {

        MediaFileDto dto = new MediaFileDto();
        dto.setId(directMessageMediaFile.getId());
        dto.setExtension(directMessageMediaFile.getExtension());
        return dto;
    }

    public static MediaFileDto fromGroupMessageMediaFile(GroupMessageMediaFile groupMessageMediaFile) {

        MediaFileDto dto = new MediaFileDto();
        dto.setId(groupMessageMediaFile.getId());
        dto.setExtension(groupMessageMediaFile.getExtension());
        return dto;
    }
}
