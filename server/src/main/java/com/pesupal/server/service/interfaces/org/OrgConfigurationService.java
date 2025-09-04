package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgConfiguration;

public interface OrgConfigurationService {

    OrgConfiguration getOrgConfigurationByOrgAndRole(Org org, Role role);

    boolean hasPrivilegeToAddMember(Org org, Role role);

    boolean hasPrivilegeToUpdateMember(Org org, Role role);

    boolean hasPrivilegeToCreateDepartment(Org org, Role role);

    void initializeOrgConfiguration(Org org);
}
