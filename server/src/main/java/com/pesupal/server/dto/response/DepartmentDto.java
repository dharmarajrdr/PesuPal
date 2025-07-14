package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDto {

    private String name;

    private String description;

    private UserBasicInfoDto head;

    public static DepartmentDto fromDepartmentAndOrgMember(Department department, OrgMember head) {

        DepartmentDto dto = new DepartmentDto();
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setHead(UserBasicInfoDto.fromOrgMember(head));
        return dto;
    }
}
