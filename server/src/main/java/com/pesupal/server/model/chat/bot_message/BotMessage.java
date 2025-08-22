package com.pesupal.server.model.chat.bot_message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class BotMessage extends CreationTimeAuditable {

    @ManyToOne
    @JsonIgnore
    private Bot bot;

    @ManyToOne
    @JsonIgnore
    private OrgMember receiver;

    private String title;

    private String message;

    private Boolean containsMedia;
}
