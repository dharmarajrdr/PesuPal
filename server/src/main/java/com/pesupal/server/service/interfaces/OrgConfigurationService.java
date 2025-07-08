package com.pesupal.server.service.interfaces;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgConfiguration;

public interface OrgConfigurationService {

    OrgConfiguration getOrgConfigurationByOrgAndRole(Org org, Role role);

    void initializeOrgConfiguration(Org org);
}
