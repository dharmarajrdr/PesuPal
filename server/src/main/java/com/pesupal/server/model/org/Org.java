package com.pesupal.server.model.org;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Org extends CreationTimeAuditable {

    private String displayName;

    private String uniqueName;

    @ManyToOne
    private User owner;

    private String displayPicture;

    private Boolean isActive;
}
