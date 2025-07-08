package com.pesupal.server.model.chat;

import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class DirectMessage extends CreationTimeAuditable {

    @ManyToOne
    private Org org;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String chatId;

    private String message;

    private Boolean containsMedia;

    private boolean deleted;

    @Enumerated(EnumType.STRING)
    private ReadReceipt readReceipt;

    @OneToMany(mappedBy = "directMessage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DirectMessageMediaFile> directMessageMediaFiles;
}
