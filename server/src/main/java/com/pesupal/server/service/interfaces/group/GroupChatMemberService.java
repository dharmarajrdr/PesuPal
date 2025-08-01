package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.AddGroupMemberDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.group.GroupChatMember;

import java.util.List;
import java.util.Map;

public interface GroupChatMemberService {

    GroupChatMember getGroupMemberByGroupIdAndUserId(String groupId, Long userId);

    GroupChatMember getGroupMemberByGroupIdAndUserId(Long groupId, Long userId);

    GroupDto joinGroup(String groupId);

    UserPreviewDto addMemberToGroup(AddGroupMemberDto addGroupMemberDto);

    Map<Role, List<UserPreviewDto>> getGroupMembers(String groupId);
}
