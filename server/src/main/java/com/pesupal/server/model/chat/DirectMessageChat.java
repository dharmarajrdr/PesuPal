package com.pesupal.server.model.chat;

import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class DirectMessageChat extends PublicAccessModel {

    @ManyToOne
    private OrgMember user1;

    @ManyToOne
    private OrgMember user2;

    public OrgMember getAnotherUser(OrgMember sender) {
        if (user1.equals(sender)) {
            return user2;
        } else if (user2.equals(sender)) {
            return user1;
        }
        return null; // or throw an exception if sender is not part of the chat
    }
}
