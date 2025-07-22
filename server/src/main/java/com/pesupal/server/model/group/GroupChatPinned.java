package com.pesupal.server.model.group;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupChatPinned extends BaseModel {

    @ManyToOne
    private Group group;

    @ManyToOne
    private User pinnedBy;
}
