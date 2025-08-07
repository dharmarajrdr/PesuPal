package com.pesupal.server.model.chat.group_message;

import com.pesupal.server.enums.MessageType;
import com.pesupal.server.model.CreationTimeAuditable;
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

    @Column(nullable = false)
    private String message;

    private boolean containsMedia;

    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private MessageType messageType = MessageType.USER_MESSAGE;

    @OneToMany(mappedBy = "lastReadMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupChatMember> readBy;
}
