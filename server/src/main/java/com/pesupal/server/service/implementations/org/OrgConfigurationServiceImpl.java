package com.pesupal.server.service.implementations.org;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgConfiguration;
import com.pesupal.server.repository.org.OrgConfigurationRepository;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrgConfigurationServiceImpl implements OrgConfigurationService {

    private final OrgConfigurationRepository orgConfigurationRepository;

    /**
     * Retrieves the organization configuration based on the organization and role.
     *
     * @param org
     * @param role
     * @return OrgConfiguration
     */
    @Override
    public OrgConfiguration getOrgConfigurationByOrgAndRole(Org org, Role role) {

        return orgConfigurationRepository.findByOrgAndRole(org, role).orElseThrow(() -> new IllegalArgumentException("Org configuration not found."));
    }

    /**
     * Checks if the role has the privilege to add a member to the organization.
     *
     * @param org
     * @param role
     * @return Boolean
     */
    @Override
    public boolean hasPrivilegeToAddMember(Org org, Role role) {

        OrgConfiguration orgConfiguration = getOrgConfigurationByOrgAndRole(org, role);
        return orgConfiguration.isAddMember();
    }

    /**
     * Checks if the role has the privilege to update a member in the organization.
     *
     * @param org
     * @param role
     * @return
     */
    @Override
    public boolean hasPrivilegeToUpdateMember(Org org, Role role) {

        OrgConfiguration orgConfiguration = getOrgConfigurationByOrgAndRole(org, role);
        return orgConfiguration.isUpdateMember();
    }

    /**
     * Checks if the role has the privilege to create a department
     *
     * @param org
     * @param role
     * @return
     */
    @Override
    public boolean hasPrivilegeToCreateDepartment(Org org, Role role) {

        OrgConfiguration orgConfiguration = getOrgConfigurationByOrgAndRole(org, role);
        return orgConfiguration.isCreateDepartment();
    }

    /**
     * Initializes the organization configuration for the given organization.
     *
     * @param org
     */
    @Override
    public void initializeOrgConfiguration(Org org) {

        OrgConfiguration superAdminConfiguration = OrgConfiguration.getInitialConfiguration(Role.SUPER_ADMIN);
        OrgConfiguration adminConfiguration = OrgConfiguration.getInitialConfiguration(Role.ADMIN);
        OrgConfiguration userConfiguration = OrgConfiguration.getInitialConfiguration(Role.USER);

        superAdminConfiguration.setOrg(org);
        adminConfiguration.setOrg(org);
        userConfiguration.setOrg(org);

        orgConfigurationRepository.save(superAdminConfiguration);
        orgConfigurationRepository.save(adminConfiguration);
        orgConfigurationRepository.save(userConfiguration);
    }
}
