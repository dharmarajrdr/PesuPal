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
public class GroupConfiguration extends BaseModel {

    @ManyToOne
    private Group group;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean addMember;

    private Boolean removeMember;

    private Boolean changeName;

    private Boolean changeDescription;

    private Boolean deleteGroup;

    private Boolean changeProfilePicture;

    private Boolean postMessage;

    private Boolean deleteMessage;

    private Boolean clearChat;
}
