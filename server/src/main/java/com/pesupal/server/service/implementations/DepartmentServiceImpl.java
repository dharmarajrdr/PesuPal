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
import com.pesupal.server.service.interfaces.OrgService;
import com.pesupal.server.service.interfaces.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl extends CurrentValueRetriever implements DepartmentService {

    private final OrgService orgService;
    private final UserService userService;
    private final OrgMemberService orgMemberService;
    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(OrgService orgService, UserService userService, @Lazy OrgMemberService orgMemberService, DepartmentRepository departmentRepository) {
        this.orgService = orgService;
        this.userService = userService;
        this.orgMemberService = orgMemberService;
        this.departmentRepository = departmentRepository;
    }

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
        Org org = orgMember.getOrg();
        OrgMember departmentHead = orgMemberService.getOrgMemberByPublicIdAndOrgId(createDepartmentDto.getHeadId(), org.getId());
        Department department = createDepartmentDto.toDepartment();
        department.setHead(departmentHead);
        department.setOrg(org);
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
     * Retrieves the Department of the current user in the organization.
     *
     * @return
     */
    @Override
    public DepartmentDto getMyDepartment() {

        OrgMember orgMember = getCurrentOrgMember();
        return DepartmentDto.fromDepartment(orgMember.getDepartment());
    }

    /**
     * Retrieves a Department by its public ID and organization.
     *
     * @param departmentPublicId
     * @param org
     * @return
     */
    @Override
    public Department getDepartmentByPublicIdAndOrg(String departmentPublicId, Org org) {

        return departmentRepository.findByPublicIdAndOrg(departmentPublicId, org).orElseThrow(() -> new DataNotFoundException("Department with ID " + departmentPublicId + " not found in organization."));
    }

    /**
     * Retrieves a Department by its public ID.
     *
     * @param departmentPublicId
     * @return
     */
    @Override
    public DepartmentDto getDepartmentByPublicId(String departmentPublicId) {

        OrgMember orgMember = getCurrentOrgMember();
        Org org = orgMember.getOrg();
        Department department = getDepartmentByPublicIdAndOrg(departmentPublicId, org);
        return DepartmentDto.fromDepartmentAndOrgMember(department, department.getHead());
    }
}
