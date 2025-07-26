package com.pesupal.server.model.chat;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PinnedDirectMessage extends BaseModel {

    @ManyToOne
    private OrgMember pinnedBy;

    @ManyToOne
    private DirectMessageChat chat;

    @Column(nullable = false)
    private Integer orderIndex;
}
