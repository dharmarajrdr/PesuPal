package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateDepartmentDto;
import com.pesupal.server.dto.response.DepartmentDto;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;

import java.util.List;

public interface DepartmentService {

    Department getDepartmentById(Long departmentId);

    Department createDepartment(CreateDepartmentDto createDepartmentDto);

    List<DepartmentDto> getAllDepartments(Long currentUserId, Long currentOrgId);

    Department getDepartmentByIdAndOrg(Long departmentId, Org org);

    DepartmentDto getDepartmentByIdAndOrgId(Long departmentId, Long currentUserId, Long currentOrgId);

    DepartmentDto getUserDepartment(Long currentUserId, Long currentOrgId);
}
