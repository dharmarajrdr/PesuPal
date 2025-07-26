package com.pesupal.server.model.chat;

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
public class DirectMessageReaction extends CreationTimeAuditable {

    @ManyToOne
    private DirectMessage directMessage;

    @ManyToOne
    private OrgMember reactor;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;
}
