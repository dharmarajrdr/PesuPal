package com.pesupal.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DepartmentConfig {

    @Id
    private Long departmentId;

    private Integer roleId;

    private Boolean addMember;

    private Boolean removeMember;

    private Boolean renameDepartment;

    private Boolean promoteMember;
}
