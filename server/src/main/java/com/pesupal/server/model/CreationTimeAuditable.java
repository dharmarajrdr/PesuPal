package com.pesupal.server.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class CreationTimeAuditable extends BaseModel {

    private LocalDateTime createdAt;

    protected void setCreationTime() {
        this.createdAt = LocalDateTime.now();
    }
}
