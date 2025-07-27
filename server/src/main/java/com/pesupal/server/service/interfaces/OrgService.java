package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateOrgDto;
import com.pesupal.server.model.org.Org;

public interface OrgService {

    Org getOrgById(Long orgId);

    Org createOrg(CreateOrgDto createOrgDto, String userPublicId);

    Org getOrgByPublicId(String orgId);
}
