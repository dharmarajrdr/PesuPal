package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.AddOrgMemberDto;
import com.pesupal.server.dto.response.OrgDetailDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;

import java.util.List;

public interface OrgMemberService {

    OrgMember getOrgMemberByPublicId(String publicId);

    OrgMember getOrgMemberByUserAndOrg(User user, Org org);

    OrgMember getOrgMemberByUserIdAndOrgId(Long userId, Long orgId);

    Boolean existsByUserAndOrg(User user, Org org);

    List<UserBasicInfoDto> getAllMembers(Long departmentId, OrgMember orgMember);

    Boolean existsByUserIdAndOrgId(Long userId, Long orgId);

    OrgMember joinOrgAsFirstMember(User user, Org org);

    List<OrgDetailDto> listOfOrgUserPartOf(Long userId);

    OrgMember addMemberToOrg(AddOrgMemberDto addOrgMemberDto, OrgMember orgMember, boolean firstMember);

    void validateUserIsOrgMember(User user, Org org);

    List<UserBasicInfoDto> getAllOrgMembers(OrgMember orgMember);

    String getOrgMemberImageByUserIdAndOrgId(Long userId, Long currentOrgId);

    UserPreviewDto getUserPreview(OrgMember orgMember);

    String generateTokenWithOrgMemberId(String publicUserId, String publicOrgId);
}
