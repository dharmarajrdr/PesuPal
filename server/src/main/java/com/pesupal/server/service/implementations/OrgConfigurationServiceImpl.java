package com.pesupal.server.service.implementations;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgConfiguration;
import com.pesupal.server.repository.OrgConfigurationRepository;
import com.pesupal.server.service.interfaces.OrgConfigurationService;
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
     * Initializes the organization configuration for the given organization.
     *
     * @param org
     */
    @Override
    public void initializeOrgConfiguration(Org org) {

        OrgConfiguration adminConfiguration = OrgConfiguration.getInitialConfiguration(Role.ADMIN);
        OrgConfiguration userConfiguration = OrgConfiguration.getInitialConfiguration(Role.USER);

        adminConfiguration.setOrg(org);
        userConfiguration.setOrg(org);

        orgConfigurationRepository.save(adminConfiguration);
        orgConfigurationRepository.save(userConfiguration);

    }
}
