package com.pesupal.server.model.chat.direct_message;

import com.pesupal.server.enums.MessageType;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.chat.MessageStatus;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DirectMessage extends CreationTimeAuditable {

    @ManyToOne
    private OrgMember sender;

    @ManyToOne
    private OrgMember receiver;

    @ManyToOne
    private DirectMessageChat directMessageChat;

    private String message;

    private Boolean containsMedia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus messageStatus = MessageStatus.SENT;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType messageType = MessageType.USER_MESSAGE;

    @Enumerated(EnumType.STRING)
    private ReadReceipt readReceipt;

}
