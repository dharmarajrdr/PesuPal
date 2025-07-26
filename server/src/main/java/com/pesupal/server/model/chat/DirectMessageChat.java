package com.pesupal.server.model.chat;

import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class DirectMessageChat extends PublicAccessModel {

    @ManyToOne
    private OrgMember orgMember1;

    @ManyToOne
    private OrgMember orgMember2;

    @OneToMany
    private List<DirectMessage> directMessages;
}
