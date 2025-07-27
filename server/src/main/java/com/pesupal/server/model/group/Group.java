package com.pesupal.server.model.group;

import com.pesupal.server.enums.Visibility;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "groups")
public class Group extends PublicAccessModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Org org;

    private String description;

    private String displayPicture;

    @ManyToOne
    private OrgMember owner;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private boolean active;

    private boolean showOldMessagesToNewJoiners;

    private boolean inactiveMemberAccessChat;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupChatMember> members;
}
