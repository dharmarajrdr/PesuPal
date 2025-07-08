package com.pesupal.server.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class CreationTimeAuditable extends BaseModel {

    private LocalDateTime createdAt;

    @PrePersist
    private void setCreationTime() {
        this.createdAt = LocalDateTime.now();
    }
}
