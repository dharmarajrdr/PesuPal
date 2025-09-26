package com.pesupal.server.model.module;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class RecordNote extends CreationTimeAuditable {

    @ManyToOne
    private ModuleRecord record;

    @ManyToOne
    private OrgMember createdBy;

    private String title;

    @Column(nullable = false)
    private String content;

}
