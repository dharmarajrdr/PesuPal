package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.request.org.OrgConfigurationDto;
import com.pesupal.server.enums.OrgAction;
import com.pesupal.server.model.org.OrgRole;
import com.pesupal.server.model.user.OrgMember;

public interface OrgConfigurationService {

    boolean hasPrivilegeTo(OrgAction orgAction, OrgRole role);

    void initializeOrgConfiguration(OrgMember owner);

    void createConfiguration(OrgConfigurationDto createOrgConfigurationDto, OrgMember currentOrgMember);

    void removeConfiguration(OrgConfigurationDto removeConfigurationDto, OrgMember currentOrgMember);
}
