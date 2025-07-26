package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.AddGroupMemberDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.user.OrgMember;

import java.util.List;
import java.util.Map;

public interface GroupChatMemberService {

    GroupChatMember getGroupMemberByGroupIdAndUserId(Long groupId, Long userId);

    boolean isUserMemberOfGroup(String groupPublicId, Long orgMemberPublicId);

    GroupDto joinGroup(Long groupId, Long currentUserId, Long currentOrgId);

    UserPreviewDto addMemberToGroup(AddGroupMemberDto addGroupMemberDto, Long userId, Long orgId);

    Map<Role, List<UserPreviewDto>> getGroupMembers(Long groupId, Long userId, Long orgId);

    GroupChatMember getGroupMemberByGroupPublicIdAndOrgMember(String groupPublicId, OrgMember orgMember);
}
