package com.pesupal.server.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.MediaUploadDto;
import com.pesupal.server.model.CreationTimeAuditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectMessageMediaFile extends CreationTimeAuditable {

    @OneToOne
    @JsonIgnore
    private DirectMessage directMessage;

    @Column(nullable = false, unique = true)
    private UUID mediaId;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long size;

    public static DirectMessageMediaFile fromMediaUploadDto(MediaUploadDto mediaUploadDto) {
        DirectMessageMediaFile mediaFile = new DirectMessageMediaFile();
        mediaFile.setMediaId(mediaUploadDto.getName());
        mediaFile.setExtension(mediaUploadDto.getExtension());
        mediaFile.setSize(mediaUploadDto.getSize());
        return mediaFile;
    }
}
