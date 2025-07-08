package com.pesupal.server.model.department;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Entity
public class DepartmentConfiguration extends BaseModel {

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean addMember;

    private Boolean removeMember;

    private Boolean updateDepartmentInfo;

    private Boolean updateRole;
}
