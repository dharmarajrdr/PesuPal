package com.pesupal.server.model.chat.group_message;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupMessagePinned extends CreationTimeAuditable {

    @ManyToOne
    private GroupChatMessage message;

    @ManyToOne
    private OrgMember pinnedBy;
}
