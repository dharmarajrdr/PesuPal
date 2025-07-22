package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.AddGroupMemberDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.model.group.GroupChatMember;

public interface GroupChatMemberService {

    GroupChatMember getGroupMemberByGroupIdAndUserId(Long groupId, Long userId);

    GroupDto joinGroup(Long groupId, Long currentUserId, Long currentOrgId);

    UserPreviewDto addMemberToGroup(AddGroupMemberDto addGroupMemberDto, Long userId, Long orgId);
}
