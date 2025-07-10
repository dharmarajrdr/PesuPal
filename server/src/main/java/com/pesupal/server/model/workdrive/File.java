package com.pesupal.server.model.workdrive;

import com.pesupal.server.enums.Security;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class File extends CreationTimeAuditable {

    private String name;

    @ManyToOne
    private Folder folder;

    @ManyToOne
    private User creator;

    @Column(nullable = false, unique = true)
    private UUID mediaId;

    @Enumerated(EnumType.STRING)
    private Security security;
}
