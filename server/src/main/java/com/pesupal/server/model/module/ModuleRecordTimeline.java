package com.pesupal.server.model.module;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleRecordTimeline extends CreationTimeAuditable {

    @ManyToOne
    private ModuleRecord record;

    @Column(nullable = false)
    private String action;

    @ManyToOne
    private OrgMember actionPerformedBy;
}
