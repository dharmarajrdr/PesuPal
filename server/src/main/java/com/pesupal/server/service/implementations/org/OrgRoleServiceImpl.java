package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.org.OrgRoleDto;
import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.org.OrgRole;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.org.OrgMemberRepository;
import com.pesupal.server.repository.org.OrgRoleRepository;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import com.pesupal.server.service.interfaces.org.OrgRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrgRoleServiceImpl implements OrgRoleService {

    private final OrgRoleRepository orgRoleRepository;
    private final OrgMemberRepository orgMemberRepository;
    private final OrgConfigurationService orgConfigurationService;

    /**
     * Create new org role
     *
     * @param name
     * @param orgMember
     * @return
     */
    @Override
    public OrgRole createOrgRoleInternal(String name, OrgMember orgMember) {

        return orgRoleRepository.save(OrgRole.builder().createdBy(orgMember).name(name).build());
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
     * @param name
     * @param currentOrgMember
     * @return
     */
    @Override
    public OrgRoleDto createOrgRole(String name, OrgMember currentOrgMember) {

        if (!orgConfigurationService.hasPrivilegeTo(OrgAction.CREATE_ORG_ROLE, currentOrgMember.getRole())) {
            throw new PermissionDeniedException("You don't have permission to create new org role.");
        }

        if (orgRoleRepository.existsByCreatedBy_OrgAndName(currentOrgMember.getOrg(), name)) {
            throw new ActionProhibitedException("Role '" + name + "' already exists.");
        }

        OrgRole orgRole = createOrgRoleInternal(name, currentOrgMember);
        OrgRoleDto orgRoleDto = OrgRoleDto.fromOrgRole(orgRole);
        orgRoleDto.setCreatedBy(UserPreviewDto.fromOrgMember(orgRole.getCreatedBy()));
        return orgRoleDto;
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
}
