package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateDepartmentDto;
import com.pesupal.server.model.department.Department;

public interface DepartmentService {

    Department getDepartmentById(Long departmentId);

    Department createDepartment(CreateDepartmentDto createDepartmentDto);
}
