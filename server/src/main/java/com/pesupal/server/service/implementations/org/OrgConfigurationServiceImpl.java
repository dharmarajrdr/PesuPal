package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.org.CreateRoleDto;
import com.pesupal.server.dto.request.org.OrgConfigurationDto;
import com.pesupal.server.dto.response.org.OrgActionDto;
import com.pesupal.server.dto.response.org.OrgActionRolesDto;
import com.pesupal.server.dto.response.org.OrgRoleDto;
import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgConfiguration;
import com.pesupal.server.model.org.OrgRole;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.org.OrgConfigurationRepository;
import com.pesupal.server.repository.org.OrgMemberRepository;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import com.pesupal.server.service.interfaces.org.OrgRoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrgConfigurationServiceImpl implements OrgConfigurationService {

    private final OrgRoleService orgRoleService;
    private final OrgMemberRepository orgMemberRepository;
    private final OrgConfigurationRepository orgConfigurationRepository;

    public OrgConfigurationServiceImpl(@Lazy OrgRoleService orgRoleService, OrgConfigurationRepository orgConfigurationRepository, OrgMemberRepository orgMemberRepository) {
        this.orgRoleService = orgRoleService;
        this.orgMemberRepository = orgMemberRepository;
        this.orgConfigurationRepository = orgConfigurationRepository;
    }

    /**
     * Checks if the role has the privilege to add a member to the organization.
     *
     * @param role
     * @return Boolean
     */
    @Override
    public boolean hasPrivilegeTo(OrgAction orgAction, OrgRole role) {

        return orgConfigurationRepository.existsByRoleAndPermittedAction(role, orgAction);
    }

    /**
     * Initializes the organization configuration for the given organization.
     *
     * @param owner
     */
    @Override
    public void initializeOrgConfiguration(OrgMember owner) {

        CreateRoleDto createSuperAdminRoleDto = CreateRoleDto.builder().name("Super Admin").description("Role with all permissions").build();
        CreateRoleDto createMemberRoleDto = CreateRoleDto.builder().name("Member").description("Default role with limited permissions").build();

        OrgRole superAdmin = orgRoleService.createOrgRoleInternal(createSuperAdminRoleDto, owner);
        OrgRole member = orgRoleService.createOrgRoleInternal(createMemberRoleDto, owner);

        List<OrgConfiguration> superAdminConfigurations = OrgConfiguration.getInitialConfiguration(superAdmin);
        List<OrgConfiguration> userConfigurations = OrgConfiguration.getInitialConfiguration(member);

        orgConfigurationRepository.saveAll(superAdminConfigurations);
        orgConfigurationRepository.saveAll(userConfigurations);

        owner.setRole(superAdmin);
        orgMemberRepository.save(owner);
    }

    /**
     * Create new org configuration
     *
     * @param createOrgConfigurationDto
     * @return
     */
    @Override
    public void createConfiguration(OrgConfigurationDto createOrgConfigurationDto, OrgMember currentOrgMember) {

        Long roleId = createOrgConfigurationDto.getRoleId();
        OrgAction orgAction = createOrgConfigurationDto.getAction();
        OrgRole orgRole = orgRoleService.getRoleById(roleId);

        if (!orgRole.getCreatedBy().getId().equals(currentOrgMember.getId())) {
            throw new PermissionDeniedException("You don't have permission to update configuration for this role.");
        }

        if (orgConfigurationRepository.existsByRoleAndPermittedAction(orgRole, orgAction)) {
            throw new DuplicateDataReceivedException("Configuration for action '" + orgAction.name() + "' and role '" + orgRole.getName() + "' already exists.");
        }

        OrgConfiguration orgConfiguration = OrgConfiguration.builder().permittedAction(orgAction).role(orgRole).build();
        orgConfigurationRepository.save(orgConfiguration);
    }

    /**
     * Revoke the permission
     *
     * @param removeConfigurationDto
     * @return
     */
    @Override
    public void removeConfiguration(OrgConfigurationDto removeConfigurationDto, OrgMember currentOrgMember) {

        Long roleId = removeConfigurationDto.getRoleId();
        OrgAction orgAction = removeConfigurationDto.getAction();
        OrgRole orgRole = orgRoleService.getRoleById(roleId);

        if (!orgRole.getCreatedBy().getId().equals(currentOrgMember.getId())) {
            throw new PermissionDeniedException("You don't have permission to update configuration for this role.");
        }

        OrgConfiguration orgConfiguration = orgConfigurationRepository.findByRoleAndPermittedAction(orgRole, orgAction)
                .orElseThrow(() -> new DataNotFoundException("No configuration found for action '" + orgAction.name() + "' and role '" + orgRole.getName() + "'."));

        orgConfigurationRepository.delete(orgConfiguration);
    }

    /**
     * Get permitted actions in the organization
     *
     * @param currentOrgMember
     * @return
     */
    @Override
    public List<OrgActionRolesDto> getPermittedActionsInOrg(OrgMember currentOrgMember) {

        Org org = currentOrgMember.getOrg();
        Map<OrgAction, List<OrgRoleDto>> permittedActionsMap = new HashMap<>();
        List<OrgActionRolesDto> permittedActionsList = new ArrayList<>();

        List<OrgConfiguration> orgConfigurations = orgConfigurationRepository.findAllByRole_CreatedBy_Org(org);
        for (OrgConfiguration config : orgConfigurations) {
            OrgAction orgAction = config.getPermittedAction();
            OrgRoleDto orgRole = OrgRoleDto.fromOrgRole(config.getRole());
            permittedActionsMap.computeIfAbsent(orgAction, k -> new ArrayList<>()).add(orgRole);
        }

        for (Map.Entry<OrgAction, List<OrgRoleDto>> entry : permittedActionsMap.entrySet()) {
            OrgActionRolesDto dto = new OrgActionRolesDto(OrgActionDto.fromOrgAction(entry.getKey()), entry.getValue());
            permittedActionsList.add(dto);
        }

        return permittedActionsList;
    }
}
