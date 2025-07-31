package com.pesupal.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class CreationTimeAuditable extends BaseModel {

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void setCreationTime() {
        this.createdAt = LocalDateTime.now();
    }
}
