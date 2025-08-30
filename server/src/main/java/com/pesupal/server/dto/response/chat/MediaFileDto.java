package com.pesupal.server.dto.response.chat;

import com.pesupal.server.model.chat.direct_message.DirectMessageMediaFile;
import com.pesupal.server.model.chat.group_message.GroupMessageMediaFile;
import lombok.Data;

import java.net.URL;

@Data
public class MediaFileDto {

    private Long id;

    private String name;

    private String extension;

    private URL mediaUrl;

    public static MediaFileDto fromDirectMessageMediaFile(DirectMessageMediaFile directMessageMediaFile) {

        MediaFileDto dto = new MediaFileDto();
        dto.setId(directMessageMediaFile.getId());
        dto.setName(directMessageMediaFile.getName());
        dto.setExtension(directMessageMediaFile.getExtension());
        return dto;
    }

    public static MediaFileDto fromGroupMessageMediaFile(GroupMessageMediaFile groupMessageMediaFile) {

        MediaFileDto dto = new MediaFileDto();
        dto.setId(groupMessageMediaFile.getId());
        dto.setName(groupMessageMediaFile.getName());
        dto.setExtension(groupMessageMediaFile.getExtension());
        return dto;
    }
}
