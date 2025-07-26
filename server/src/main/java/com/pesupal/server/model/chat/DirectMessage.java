package com.pesupal.server.model.chat;

import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DirectMessage extends CreationTimeAuditable {

    @ManyToOne
    private Org org;

    @ManyToOne
    private OrgMember sender;

    @ManyToOne
    private OrgMember receiver;

    @ManyToOne
    private DirectMessageChat directMessageChat;

    private String message;

    private Boolean containsMedia;

    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private ReadReceipt readReceipt;

}
