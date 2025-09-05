package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.org.CreateRoleDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.org.OrgRoleDto;
import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgRole;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.org.OrgMemberRepository;
import com.pesupal.server.repository.org.OrgRoleRepository;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import com.pesupal.server.service.interfaces.org.OrgRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrgRoleServiceImpl implements OrgRoleService {

    private final OrgRoleRepository orgRoleRepository;
    private final OrgMemberRepository orgMemberRepository;
    private final OrgConfigurationService orgConfigurationService;

    /**
     * Convert OrgRole to OrgRoleDto
     *
     * @param orgRole
     * @return
     */
    private OrgRoleDto toOrgRoleDto(OrgRole orgRole) {

        OrgRoleDto orgRoleDto = OrgRoleDto.fromOrgRole(orgRole);
        orgRoleDto.setCreatedBy(UserPreviewDto.fromOrgMember(orgRole.getCreatedBy()));
        orgRoleDto.setMemberCount(orgMemberRepository.countByRole(orgRole));
        return orgRoleDto;
    }

    /**
     * Create new org role
     *
     * @param createRoleDto
     * @param orgMember
     * @return
     */
    @Override
    public OrgRole createOrgRoleInternal(CreateRoleDto createRoleDto, OrgMember orgMember) {

        OrgRole orgRole = createRoleDto.toOrgRole();
        orgRole.setCreatedBy(orgMember);
        return orgRoleRepository.save(orgRole);
    }

    /**
     * Find role by it's id
     *
     * @param roleId
     * @return
     */
    @Override
    public OrgRole getRoleById(Long roleId) {

        return orgRoleRepository.findById(roleId).orElseThrow(() -> new DataNotFoundException("Role not found."));
    }

    /**
     * Create new role
     *
     * @param createRoleDto
     * @param currentOrgMember
     * @return
     */
    @Override
    public OrgRoleDto createOrgRole(CreateRoleDto createRoleDto, OrgMember currentOrgMember) {

        if (!orgConfigurationService.hasPrivilegeTo(OrgAction.CREATE_ORG_ROLE, currentOrgMember.getRole())) {
            throw new PermissionDeniedException("You don't have permission to create new org role.");
        }

        String name = createRoleDto.getName().trim();

        if (orgRoleRepository.existsByCreatedBy_OrgAndName(currentOrgMember.getOrg(), name)) {
            throw new ActionProhibitedException("Role '" + name + "' already exists.");
        }

        OrgRole orgRole = createOrgRoleInternal(createRoleDto, currentOrgMember);
        return toOrgRoleDto(orgRole);
    }

    /**
     * Delete role by id
     *
     * @param roleId
     */
    @Override
    public void deleteRoleById(Long roleId, OrgMember orgMember) {

        OrgRole orgRole = getRoleById(roleId);
        if (!orgRole.getCreatedBy().getId().equals(orgMember.getId())) {
            throw new PermissionDeniedException("You don't have permission to delete this role.");
        }

        boolean anyUserWithThisRole = orgMemberRepository.existsByRole(orgRole);
        if (anyUserWithThisRole) {
            throw new ActionProhibitedException("This role is assigned to one or more users. You cannot delete it.");
        }

        if (orgRole.getName().equals("Super Admin")) {
            throw new ActionProhibitedException("Role 'Super Admin' cannot be deleted.");
        }

        orgRoleRepository.delete(orgRole);
    }

    /**
     * Get role by org and name
     *
     * @param org
     * @param superAdmin
     * @return
     */
    @Override
    public OrgRole getRoleByOrgAndName(Org org, String superAdmin) {

        return orgRoleRepository.findByNameAndCreatedBy_Org(superAdmin, org).orElseThrow(() -> new DataNotFoundException("Role '" + superAdmin + "' not found."));
    }

    /**
     * Get all roles in the org
     *
     * @param currentOrgMember
     * @return
     */
    @Override
    public List<OrgRoleDto> getAllRoles(OrgMember currentOrgMember) {

        Org org = currentOrgMember.getOrg();

        List<OrgRole> orgRoles = orgRoleRepository.findAllByCreatedBy_OrgOrderByName(org);
        return orgRoles.stream().map(this::toOrgRoleDto).toList();
    }

    /**
     * Update org role
     *
     * @param roleId
     * @param updateRoleDto
     * @param currentOrgMember
     * @return
     */
    @Override
    public OrgRoleDto updateOrgRole(Long roleId, CreateRoleDto updateRoleDto, OrgMember currentOrgMember) {

        OrgRole orgRole = getRoleById(roleId);
        if (!orgRole.getCreatedBy().getId().equals(currentOrgMember.getId())) {
            throw new PermissionDeniedException("You don't have permission to update this role.");
        }

        updateRoleDto.applyOrgRole(orgRole);
        orgRoleRepository.save(orgRole);
        return toOrgRoleDto(orgRole);
    }
}
