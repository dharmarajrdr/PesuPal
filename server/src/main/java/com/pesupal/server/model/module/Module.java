package com.pesupal.server.model.module;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    private boolean allowDuplicateSubject;

    private boolean openToRelation;

    private boolean active;
}
