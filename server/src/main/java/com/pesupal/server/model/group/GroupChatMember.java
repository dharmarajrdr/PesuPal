package com.pesupal.server.model.group;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupChatMember extends CreationTimeAuditable {

    @ManyToOne
    private Group group;

    @ManyToOne
    private OrgMember participant;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;

    @ManyToOne
    private GroupChatMessage lastReadMessage;
}
