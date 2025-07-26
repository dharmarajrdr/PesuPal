package com.pesupal.server.helpers;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.exceptions.MandatoryDataMissingException;
import com.pesupal.server.exceptions.OrganizationNotSelectedException;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.security.CustomUserDetails;
import com.pesupal.server.security.SecurityUtil;
import com.pesupal.server.service.interfaces.OrgMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentValueRetriever {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private OrgMemberService orgMemberService;

    /**
     * @return
     * @deprecated
     */
    protected Long getCurrentUserId() {

        return securityUtil.getCurrentUserId();
    }

    protected CustomUserDetails getCurrentUserDetails() {

        return securityUtil.getCurrentUserDetails();
    }

    protected String getCurrentOrgMemberPublicId() {

        return getCurrentUserDetails().getOrgMemberPublicId();
    }

    protected OrgMember getCurrentOrgMember() {

        String orgMemberPublicId = getCurrentOrgMemberPublicId();
        if (orgMemberPublicId == null) {
            throw new OrganizationNotSelectedException();
        }
        return orgMemberService.getOrgMemberByPublicId(orgMemberPublicId);
    }

    protected Long getCurrentOrgId() {

        Long orgId = RequestContext.getLong("X-ORG-ID");
        if (orgId == null) {
            throw new MandatoryDataMissingException("X-ORG-ID header is required");
        }
        return orgId;
    }
}
