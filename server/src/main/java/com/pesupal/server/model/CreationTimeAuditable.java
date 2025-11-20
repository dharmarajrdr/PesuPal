package com.pesupal.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class CreationTimeAuditable extends BaseModel {

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void setCreationTime() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
