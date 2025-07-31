package com.pesupal.server.model.module;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class ModuleRecordTimeline extends CreationTimeAuditable {

    @ManyToOne
    private ModuleField field;

    @ManyToOne
    private ModuleRecord record;

    private String previousValue;

    private String newValue;

    @ManyToOne
    private OrgMember actionPerformedBy;
}
