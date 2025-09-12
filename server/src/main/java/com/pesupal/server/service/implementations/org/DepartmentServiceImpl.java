package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.org.CreateDepartmentDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.org.DepartmentDto;
import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.department.Department;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.org.DepartmentRepository;
import com.pesupal.server.service.interfaces.org.DepartmentService;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl extends CurrentValueRetriever implements DepartmentService {

    private final OrgMemberService orgMemberService;
    private final DepartmentRepository departmentRepository;
    private final OrgConfigurationService orgConfigurationService;

    /**
     * Retrieves a Department by its ID.
     *
     * @param departmentId
     * @return Department
     */
    @Override
    public Department getDepartmentById(String departmentId) {

        return departmentRepository.findByPublicId(departmentId).orElseThrow(() -> new DataNotFoundException("Department with ID " + departmentId + " not found."));
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

        if (!orgConfigurationService.hasPrivilegeTo(OrgAction.CREATE_DEPARTMENT, orgMember.getRole())) {
            throw new PermissionDeniedException("You don't have permission to create department.");
        }

        if (departmentRepository.existsByOrgAndName(orgMember.getOrg(), createDepartmentDto.getName())) {
            throw new DuplicateDataReceivedException("Department with same name already exists.");
        }

        Department department = createDepartmentDto.toDepartment();
        if (createDepartmentDto.getHeadId() != null) {
            department.setHead(orgMemberService.getOrgMemberByPublicId(createDepartmentDto.getHeadId()));
        }
        if (createDepartmentDto.getParentId() != null) {
            department.setParent(getDepartmentById(createDepartmentDto.getParentId()));
        }
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
        // Assuming head is not needed here, otherwise fetch it
        return departmentRepository.findAllByOrgOrderByOrg_DisplayNameAsc(orgMember.getOrg()).stream().map(DepartmentDto::fromDepartment).toList();
    }

    /**
     * Retrieves a Department by its public ID.
     *
     * @param departmentId
     * @return
     */
    public Department getDepartmentByPublicId(String departmentId) {

        return departmentRepository.findByPublicId(departmentId).orElseThrow(() -> new DataNotFoundException("Department with ID " + departmentId + " not found"));
    }

    /**
     * Retrieves a Department by its ID and organization ID.
     *
     * @param departmentId
     * @return
     */
    @Override
    public DepartmentDto getDepartmentByIdAndOrgId(String departmentId) {

        OrgMember orgMember = getCurrentOrgMember();
        Department department = getDepartmentByPublicId(departmentId);

        if (!department.getOrg().getId().equals(orgMember.getOrg().getId())) {
            throw new PermissionDeniedException("You do not have permission to access this department.");
        }

        DepartmentDto departmentDto = DepartmentDto.fromDepartment(department);
        if (department.getHead() != null) {
            departmentDto.setHead(orgMemberService.getUserBasicInfo(department.getHead()));
        }
        return departmentDto;
    }

    /**
     * Retrieves the Department of the current user in the organization.
     *
     * @return
     */
    @Override
    public DepartmentDto getUserDepartment() {

        OrgMember orgMember = getCurrentOrgMember();
        DepartmentDto departmentDto = DepartmentDto.fromDepartment(orgMember.getDepartment());
        departmentDto.setHead(orgMemberService.getUserBasicInfo(orgMember));
        return departmentDto;
    }


    /**
     * Retrieves all members of a department.
     *
     * @param departmentId
     * @return List<UserBasicInfoDto>
     */
    @Override
    public List<UserBasicInfoDto> getAllMembers(String departmentId) {

        OrgMember orgMember = getCurrentOrgMember();

        Department department = getDepartmentByPublicId(departmentId);
        if (!department.getOrg().getId().equals(orgMember.getOrg().getId())) {
            throw new PermissionDeniedException("You do not have permission to access members of this department.");
        }

        return department.getMembers().stream().map(orgMemberService::getUserBasicInfo).sorted((a, b) -> a.getDisplayName().compareToIgnoreCase(b.getDisplayName())).toList();
    }

}
