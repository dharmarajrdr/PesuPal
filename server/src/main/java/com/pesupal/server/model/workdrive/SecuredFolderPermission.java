package com.pesupal.server.model.workdrive;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SecuredFolderPermission extends CreationTimeAuditable {

    @ManyToOne
    private Folder folder;

    @ManyToOne
    private User user;

    private boolean readable;

    private boolean writable;

    private LocalDateTime expiryDate;
}
