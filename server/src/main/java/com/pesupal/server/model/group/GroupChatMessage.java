package com.pesupal.server.model.group;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class GroupChatMessage extends CreationTimeAuditable {

    @ManyToOne
    private Group group;

    @ManyToOne
    private User sender;

    private String message;

    private boolean containsMedia;

    private boolean deleted;

    private String readStatus;

    @OneToMany(mappedBy = "lastReadMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupChatMember> readBy;
}
