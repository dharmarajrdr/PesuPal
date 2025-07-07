package com.pesupal.server.model.user;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class OrgMember extends CreationTimeAuditable {

    @ManyToOne
    private Org org;

    @ManyToOne
    private User user;

    private String userName;

    private String displayName;

    private Long employeeId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Designation designation;

    private String status;

    private String displayPicture;

    @ManyToOne
    private User managedBy;

    private Boolean isRemoved;

    @ManyToOne
    private Department department;
}
