package com.pesupal.server.model.module;

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
    private OrgMember createdBy;

    private boolean active;
}
