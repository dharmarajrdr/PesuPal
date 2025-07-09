package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.AddOrgMemberDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;

public interface OrgMemberService {

    OrgMember getOrgMemberByUserAndOrg(User user, Org org);

    OrgMember getOrgMemberByUserIdAndOrgId(Long userId, Long orgId);

    OrgMember joinOrgAsFirstMember(User user, Org org);

    OrgMember addMemberToOrg(AddOrgMemberDto addOrgMemberDto, Long adminId, Long orgId, boolean firstMember);
}
