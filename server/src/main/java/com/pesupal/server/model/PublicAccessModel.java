package com.pesupal.server.model;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.helpers.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class PublicAccessModel extends CreationTimeAuditable {

    /**
     * It is used to access the entity publicly without exposing the internal database ID.
     */
    @Column(unique = true, updatable = false, nullable = false)
    private String publicId;

    private void generatePublicId() {

        if (this.publicId == null) {
            this.publicId = IdGenerator.generateRandomId(StaticConfig.PUBLIC_KEY_LENGTH);
        }
    }
}