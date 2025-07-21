package com.pesupal.server.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.FileType;
import com.pesupal.server.model.CreationTimeAuditable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DirectMessageMediaFile extends CreationTimeAuditable {

    @OneToOne
    @JsonIgnore
    private DirectMessage directMessage;

    private FileType fileType;

    private Long fileSize;

    private String fileUrl;
}
