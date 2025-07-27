package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateDepartmentDto;
import com.pesupal.server.dto.response.DepartmentDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.DepartmentRepository;
import com.pesupal.server.service.interfaces.DepartmentService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl extends CurrentValueRetriever implements DepartmentService {

    private final OrgMemberService orgMemberService;
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
     * @return Department
     */
    @Override
    public Department createDepartment(CreateDepartmentDto createDepartmentDto) {

        OrgMember orgMember = getCurrentOrgMember();
        OrgMember departmentHead = orgMemberService.getOrgMemberByPublicId(createDepartmentDto.getHeadId());
        Department department = createDepartmentDto.toDepartment();
        department.setHead(departmentHead);
        department.setOrg(orgMember.getOrg());
        return departmentRepository.save(department);
    }

    /**
     * Retrieves all Departments in the organization.
     *
     * @return List of Departments
     */
    @Override
    public List<DepartmentDto> getAllDepartments() {

        OrgMember orgMember = getCurrentOrgMember();
        return departmentRepository.findAllByOrgOrderByOrg_DisplayNameAsc(orgMember.getOrg()).stream().map(department -> {
            return DepartmentDto.fromDepartmentAndOrgMember(department, null);  // Assuming head is not needed here, otherwise fetch it
        }).toList();
    }

    /**
     * @param departmentId
     * @param org
     * @return
     */
    @Override
    public Department getDepartmentByIdAndOrg(Long departmentId, Org org) {

        return departmentRepository.findByIdAndOrg(departmentId, org).orElseThrow(() -> new DataNotFoundException("Department with ID " + departmentId + " not found"));
    }

    /**
     * Retrieves a Department by its ID and organization ID.
     *
     * @param departmentId
     * @return
     */
    @Override
    public DepartmentDto getDepartmentByIdAndOrgId(Long departmentId) {

        OrgMember orgMember = getCurrentOrgMember();
        Department department = getDepartmentByIdAndOrg(departmentId, orgMember.getOrg());

        return DepartmentDto.fromDepartmentAndOrgMember(department, department.getHead());
    }

    /**
     * Retrieves the Department of the current user in the organization.
     *
     * @return
     */
    @Override
    public DepartmentDto getUserDepartment() {

        OrgMember orgMember = getCurrentOrgMember();
        return DepartmentDto.fromDepartmentAndOrgMember(orgMember.getDepartment(), orgMember);
    }
}
