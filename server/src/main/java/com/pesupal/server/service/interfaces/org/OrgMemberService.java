package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.request.org.AddOrgMemberDto;
import com.pesupal.server.dto.request.org.CreateOrgDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.org.OrgDetailDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgInvitation;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Pageable;

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

    void addMemberToOrg(AddOrgMemberDto addOrgMemberDto, OrgMember orgMember) throws MessagingException;

    void validateUserIsOrgMember(User user, Org org);

    List<UserBasicInfoDto> getAllOrgMembers(OrgMember orgMember);

    List<UserBasicInfoDto> getSearchedOrgMembers(OrgMember orgMember, String search, Pageable pageable);

    UserPreviewDto getUserPreview(OrgMember orgMember);

    String generateTokenWithOrgMemberId(String publicUserId, String publicOrgId);

    void removeAllOrgMembers(Org org);

    void updateOrgMember(String orgMemberPublicId, AddOrgMemberDto addOrgMemberDto, OrgMember currentOrgMember);

    void joinInOrg(OrgInvitation orgInvitation, User user);

    void joinInAllInvitedOrgs(User user);

    List<UserBasicInfoDto> getAllSuperAdmins(OrgMember currentOrgMember);

    List<UserBasicInfoDto> getAllInactiveMembers(OrgMember currentOrgMember);
}
