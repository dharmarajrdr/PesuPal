package com.pesupal.server.dto.request;

import com.pesupal.server.model.department.Department;
import lombok.Data;

@Data
public class CreateDepartmentDto {

    private String name;

    private String description;

    private Long orgId;

    private Long headId;

    public Department toDepartment() {

        Department department = new Department();
        department.setName(this.name);
        department.setDescription(this.description);
        return department;
    }
}
