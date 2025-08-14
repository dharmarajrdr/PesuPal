package com.pesupal.server.model.chat.group_message;

import com.pesupal.server.enums.MessageType;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.chat.MessageStatus;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class GroupChatMessage extends CreationTimeAuditable {

    @ManyToOne
    private Group group;

    @ManyToOne
    private OrgMember sender;

    private String message;

    private boolean containsMedia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus messageStatus = MessageStatus.SENT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType = MessageType.USER_MESSAGE;

    @OneToMany(mappedBy = "lastReadMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<GroupChatMember> readBy;
}
