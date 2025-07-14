package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateDepartmentDto;
import com.pesupal.server.dto.response.DepartmentDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.DepartmentRepository;
import com.pesupal.server.service.interfaces.DepartmentService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.OrgService;
import com.pesupal.server.service.interfaces.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

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

        Org org = orgService.getOrgById(createDepartmentDto.getOrgId());
        User departmentHead = userService.getUserById(createDepartmentDto.getHeadId());
        Department department = createDepartmentDto.toDepartment();
        department.setHead(departmentHead);
        department.setOrg(org);
        return departmentRepository.save(department);
    }

    /**
     * Retrieves all Departments in the organization.
     *
     * @param userId
     * @param orgId
     * @return List of Departments
     */
    @Override
    public List<DepartmentDto> getAllDepartments(Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
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
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public DepartmentDto getDepartmentByIdAndOrgId(Long departmentId, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        Department department = getDepartmentByIdAndOrg(departmentId, orgMember.getOrg());

        return DepartmentDto.fromDepartmentAndOrgMember(
                department,
                orgMemberService.getOrgMemberByUserAndOrg(department.getHead(), orgMember.getOrg())
        );
    }
}
