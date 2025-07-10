package com.pesupal.server.helpers;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.exceptions.MandatoryDataMissingException;
import com.pesupal.server.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentValueRetriever {

    @Autowired
    private SecurityUtil securityUtil;

    protected Long getCurrentUserId() {

        return securityUtil.getCurrentUserId();
    }

    protected Long getCurrentOrgId() {

        Long orgId = RequestContext.getLong("X-ORG-ID");
        if (orgId == null) {
            throw new MandatoryDataMissingException("X-ORG-ID header is required");
        }
        return orgId;
    }
}
