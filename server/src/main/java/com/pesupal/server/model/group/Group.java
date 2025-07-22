package com.pesupal.server.model.group;

import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "groups")
public class Group extends CreationTimeAuditable {

    @ManyToOne
    private Org org;

    private String name;

    private String description;

    @ManyToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private boolean active;

    private boolean showOldMessagesToNewJoiners;
}
