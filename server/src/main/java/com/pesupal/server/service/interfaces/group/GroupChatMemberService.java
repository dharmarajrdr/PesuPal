package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.AddGroupMemberDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.user.OrgMember;

public interface GroupChatMemberService {

    void initializeGroupChatMember(Group group, OrgMember orgMember);

    GroupChatMember getGroupMemberByGroupIdAndUserId(Long groupId, Long userId);

    GroupDto joinGroup(Long groupId, Long currentUserId, Long currentOrgId);

    UserPreviewDto addMemberToGroup(AddGroupMemberDto addGroupMemberDto, Long userId, Long orgId);
}
