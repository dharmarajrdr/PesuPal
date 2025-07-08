package com.pesupal.server.model;

import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreationTimeAuditable extends BaseModel {

    private LocalDateTime createdAt;

    @PrePersist
    public void setCreationTime() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
