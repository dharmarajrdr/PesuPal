package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateOrgDto;
import com.pesupal.server.model.org.Org;

public interface OrgService {

    public Org getOrgById(Long orgId);

    public Org createOrg(CreateOrgDto createOrgDto);
}
