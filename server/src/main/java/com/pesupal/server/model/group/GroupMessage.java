package com.pesupal.server.model.group;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupMessage extends CreationTimeAuditable {

    @ManyToOne
    private Group group;

    @ManyToOne
    private User sender;

    private String message;

    private Boolean containsMedia;

    private Boolean deleted;
}
