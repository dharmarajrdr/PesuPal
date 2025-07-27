package com.pesupal.server.model.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.MediaUploadDto;
import com.pesupal.server.model.PublicAccessModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupMessageMediaFile extends PublicAccessModel {

    @OneToOne
    @JsonIgnore
    private GroupChatMessage groupChatMessage;

    @Column(nullable = false, unique = true)
    private UUID mediaId;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long size;

    public static GroupMessageMediaFile fromMediaUploadDto(MediaUploadDto media) {

        GroupMessageMediaFile groupMessageMediaFile = new GroupMessageMediaFile();
        groupMessageMediaFile.setMediaId(media.getName());
        groupMessageMediaFile.setExtension(media.getExtension());
        groupMessageMediaFile.setSize(media.getSize());
        return groupMessageMediaFile;
    }
}
