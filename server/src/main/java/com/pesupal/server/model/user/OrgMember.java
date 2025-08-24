package com.pesupal.server.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
public class OrgMember extends PublicAccessModel {

    @ManyToOne
    @JsonIgnore
    private Org org;

    @ManyToOne
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private Integer employeeId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Designation designation;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String displayPicture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private OrgMember manager;

    private boolean archived;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private OrgMember addedBy;

    private LocalDateTime lastLoggedIn;
}
