package com.pesupal.server.model.workdrive;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
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
    private OrgMember accessGrantedTo;

    private boolean readable;

    private boolean writable;

    private LocalDateTime expiryDate;
}
