package com.pesupal.server.model.module;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ModuleMember extends CreationTimeAuditable {

    @ManyToOne
    private Module module;

    @ManyToOne
    private OrgMember orgMember;

    @Enumerated(EnumType.STRING)
    private ModuleRole role;

    private boolean active;
}
