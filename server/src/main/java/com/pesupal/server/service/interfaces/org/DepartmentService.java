package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.request.org.CreateDepartmentDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.org.DepartmentDto;
import com.pesupal.server.model.department.Department;

import java.util.List;

public interface DepartmentService {

    Department getDepartmentById(Long departmentId);

    Department createDepartment(CreateDepartmentDto createDepartmentDto);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartmentByIdAndOrgId(String departmentId);

    DepartmentDto getUserDepartment();

    List<UserBasicInfoDto> getAllMembers(String departmentId);
}
