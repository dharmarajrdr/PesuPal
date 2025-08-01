package com.pesupal.server.model.group;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class GroupChatPinned extends BaseModel {

    @ManyToOne
    private Group group;

    @ManyToOne
    private OrgMember pinnedBy;

    @Column(nullable = false)
    private Integer orderIndex;
}
