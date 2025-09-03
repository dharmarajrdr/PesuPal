package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.org.OrgConfigurationDto;
import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.DuplicateDataReceivedException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.org.OrgConfiguration;
import com.pesupal.server.model.org.OrgRole;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.org.OrgConfigurationRepository;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import com.pesupal.server.service.interfaces.org.OrgRoleService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgConfigurationServiceImpl implements OrgConfigurationService {

    private final OrgRoleService orgRoleService;
    private final OrgConfigurationRepository orgConfigurationRepository;

    public OrgConfigurationServiceImpl(@Lazy OrgRoleService orgRoleService, OrgConfigurationRepository orgConfigurationRepository) {
        this.orgRoleService = orgRoleService;
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

        OrgRole superAdmin = orgRoleService.createOrgRoleInternal("Super Admin", owner);
        OrgRole member = orgRoleService.createOrgRoleInternal("Member", owner);

        List<OrgConfiguration> superAdminConfigurations = OrgConfiguration.getInitialConfiguration(superAdmin);
        List<OrgConfiguration> userConfigurations = OrgConfiguration.getInitialConfiguration(member);

        orgConfigurationRepository.saveAll(superAdminConfigurations);
        orgConfigurationRepository.saveAll(userConfigurations);
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
}
