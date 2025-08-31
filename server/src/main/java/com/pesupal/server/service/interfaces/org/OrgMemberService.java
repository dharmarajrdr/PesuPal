package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.request.org.AddOrgMemberDto;
import com.pesupal.server.dto.request.org.CreateOrgDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.org.OrgDetailDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;

import java.util.List;

public interface OrgMemberService {

    OrgMember getOrgMemberByPublicId(String publicId);

    OrgMember getOrgMemberByUserAndOrg(User user, Org org);

    OrgMember getOrgMemberByUserIdAndOrgId(Long userId, Long orgId);

    UserBasicInfoDto getUserBasicInfo(OrgMember orgMember);

    Boolean existsByUserAndOrg(User user, Org org);

    Boolean existsByUserIdAndOrgId(Long userId, Long orgId);

    OrgMember joinOrgAsFirstMember(CreateOrgDto createOrgDto, Org org, User user);

    List<OrgDetailDto> listOfOrgUserPartOf(Long userId);

    OrgMember addMemberToOrg(AddOrgMemberDto addOrgMemberDto, OrgMember orgMember, boolean firstMember);

    void validateUserIsOrgMember(User user, Org org);

    List<UserBasicInfoDto> getAllOrgMembers(OrgMember orgMember);

    List<UserPreviewDto> getSearchedOrgMembers(OrgMember orgMember, String search, int page, int size);

    UserPreviewDto getUserPreview(OrgMember orgMember);

    String generateTokenWithOrgMemberId(String publicUserId, String publicOrgId);

    void removeAllOrgMembers(Org org);

    void updateOrgMember(String orgMemberPublicId, AddOrgMemberDto addOrgMemberDto, OrgMember currentOrgMember);

    void informPresence(OrgMember orgMember);
}
