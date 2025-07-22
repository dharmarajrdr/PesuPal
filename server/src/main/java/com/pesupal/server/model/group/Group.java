package com.pesupal.server.model.group;

import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "groups")
public class Group extends CreationTimeAuditable {

    @ManyToOne
    private Org org;

    @Column(nullable = false)
    private String name;

    private String description;

    private String displayPicture;

    @ManyToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private boolean active;

    private boolean showOldMessagesToNewJoiners;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupChatMember> members;
}
