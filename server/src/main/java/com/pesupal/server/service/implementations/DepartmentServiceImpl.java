package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateDepartmentDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.DepartmentRepository;
import com.pesupal.server.service.interfaces.DepartmentService;
import com.pesupal.server.service.interfaces.OrgService;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final OrgService orgService;
    private final UserService userService;
    private final DepartmentRepository departmentRepository;

    /**
     * Retrieves a Department by its ID.
     *
     * @param departmentId
     * @return Department
     */
    @Override
    public Department getDepartmentById(Long departmentId) {

        return departmentRepository.findById(departmentId).orElseThrow(() -> new DataNotFoundException("Department with ID " + departmentId + " not found."));
    }

    /**
     * Creates a new Department.
     *
     * @param createDepartmentDto
     * @return
     */
    @Override
    public Department createDepartment(CreateDepartmentDto createDepartmentDto) {

        Org org = orgService.getOrgById(createDepartmentDto.getOrgId());
        User departmentHead = userService.getUserById(createDepartmentDto.getHeadId());
        Department department = createDepartmentDto.toDepartment();
        department.setHead(departmentHead);
        department.setOrg(org);
        return departmentRepository.save(department);
    }
}
