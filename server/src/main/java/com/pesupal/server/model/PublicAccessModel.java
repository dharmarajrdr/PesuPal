package com.pesupal.server.model;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.helpers.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class PublicAccessModel extends BaseModel {

    /**
     * It is used to access the entity publicly without exposing the internal database ID.
     */
    @Column(unique = true, updatable = false, nullable = false)
    private String publicId;

    private LocalDateTime createdAt;

    protected void setCreationTime() {
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void generatePublicId() {

        if (this.publicId == null) {
            this.publicId = IdGenerator.generateRandomId(StaticConfig.PUBLIC_KEY_LENGTH);
        }
        setCreationTime();
    }
}