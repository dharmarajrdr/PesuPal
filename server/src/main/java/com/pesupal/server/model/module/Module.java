package com.pesupal.server.model.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Module extends PublicAccessModel {

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JsonIgnore
    private OrgMember createdBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModuleAccessibility accessibility;

    private boolean allowDuplicateSubject;

    private boolean openToRelation;

    private boolean active;
}
