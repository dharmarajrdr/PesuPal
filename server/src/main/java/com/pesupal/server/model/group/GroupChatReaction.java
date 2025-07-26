package com.pesupal.server.model.group;

import com.pesupal.server.enums.Reaction;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupChatReaction extends CreationTimeAuditable {

    @ManyToOne
    private GroupChatMessage groupChatMessage;

    @ManyToOne
    private OrgMember reactedBy;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;
}
