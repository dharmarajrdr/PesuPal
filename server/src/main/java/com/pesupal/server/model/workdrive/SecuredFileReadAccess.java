package com.pesupal.server.model.workdrive;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SecuredFileReadAccess extends CreationTimeAuditable {

    @ManyToOne
    private File file;

    @ManyToOne
    private OrgMember user;

    private LocalDateTime expiryDate;
}
