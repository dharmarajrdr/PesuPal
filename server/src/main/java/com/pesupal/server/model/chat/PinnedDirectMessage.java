package com.pesupal.server.model.chat;

import com.pesupal.server.model.BaseModel;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class PinnedDirectMessage extends BaseModel {

    @ManyToOne
    private Org org;

    @ManyToOne
    private User pinnedBy;

    @ManyToOne
    private User pinnedUser;

    @Column(nullable = false)
    private Integer orderIndex;
}
