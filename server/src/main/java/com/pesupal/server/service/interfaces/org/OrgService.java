package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.request.org.CreateOrgDto;
import com.pesupal.server.dto.response.org.OrgCreatedDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;

public interface OrgService {

    Org getOrgById(Long orgId);

    OrgCreatedDto createOrg(CreateOrgDto createOrgDto, String userPublicId);

    void deleteOrg(String orgPublicId, OrgMember orgMember);

    void updateOrg(String orgPublicId, CreateOrgDto createOrgDto, OrgMember currentOrgMember);
}
