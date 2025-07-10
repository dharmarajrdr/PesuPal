package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.AddOrgMemberDto;
import com.pesupal.server.dto.response.OrgDetailDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;

import java.util.List;

public interface OrgMemberService {

    OrgMember getOrgMemberByUserAndOrg(User user, Org org);

    OrgMember getOrgMemberByUserIdAndOrgId(Long userId, Long orgId);

    Boolean existsByUserAndOrg(User user, Org org);

    OrgMember joinOrgAsFirstMember(User user, Org org);

    List<OrgDetailDto> listOfOrgUserPartOf(Long userId);

    OrgMember addMemberToOrg(AddOrgMemberDto addOrgMemberDto, Long adminId, Long orgId, boolean firstMember);

    void validateUserIsOrgMember(User user, Org org);

    void validateUserIsOrgMember(Long userId, Long orgId);
}
