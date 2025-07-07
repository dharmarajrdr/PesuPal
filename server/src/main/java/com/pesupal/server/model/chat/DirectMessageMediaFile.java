package com.pesupal.server.model.chat;

import com.pesupal.server.enums.FileType;
import com.pesupal.server.model.CreationTimeAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DirectMessageMediaFile extends CreationTimeAuditable {

    @ManyToOne
    private DirectMessage directMessage;

    private FileType fileType;

    private Long fileSize;

    private String fileUrl;
}
