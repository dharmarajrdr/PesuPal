package com.pesupal.server.dto.response.org;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.model.department.Department;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDto {

    private String id;

    private String name;

    private String description;

    private UserBasicInfoDto head;

    public static DepartmentDto fromDepartmentAndOrgMember(Department department) {

        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getPublicId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        return dto;
    }
}
