package com.pesupal.server.service.interfaces;

import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;

public interface OrgMemberService {

    OrgMember getOrgMemberByUserAndOrg(User reactor, Org org);
}
