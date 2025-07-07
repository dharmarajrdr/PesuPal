package com.pesupal.server.model.org;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class OrgConfiguration extends BaseModel {

    @ManyToOne
    private Org org;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean inviteMember;

    private Boolean addMember;

    private Boolean removeMember;

    private Boolean updateMember;

    private Boolean updateOrg;

    private Boolean deleteOrg;

    private Boolean leaveOrg;

    private Boolean createGroup;

    private Boolean showEmployeeId;
}
