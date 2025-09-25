package com.pesupal.server.model.department;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Department extends PublicAccessModel {

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne
    private Department parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Org org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private OrgMember head;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrgMember> members;
}
