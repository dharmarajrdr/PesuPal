package com.pesupal.server.model.group;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupChatConfiguration extends BaseModel {

    @ManyToOne
    private Group group;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean addMember;

    private boolean removeMember;

    private boolean changeName;

    private boolean changeDescription;

    private boolean deleteGroup;

    private boolean leaveGroup;

    private boolean changeProfilePicture;

    private boolean viewMembers;

    private boolean postMessage;

    private boolean pinMessage;

    private boolean deleteMessage;

    private boolean clearChat;

    private boolean roleUpdate;
}
