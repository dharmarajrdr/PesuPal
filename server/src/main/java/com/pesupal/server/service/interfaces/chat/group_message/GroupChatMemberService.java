package com.pesupal.server.service.interfaces.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.AddGroupMemberDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.chat.group_message.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.chat.group_message.GroupChatMember;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GroupChatMemberService {

    GroupChatMember getGroupMemberByGroupIdAndUserId(String groupId, Long userId);

    GroupChatMember getGroupMemberByGroupIdAndUserId(Long groupId, Long userId);

    GroupDto joinGroup(String groupId);

    UserPreviewDto addMemberToGroup(AddGroupMemberDto addGroupMemberDto);

    Map<Role, List<UserPreviewDto>> getGroupMembers(String groupId);

    void leaveGroup(String groupId);

    List<UserPreviewDto> getNonParticipantMembers(String groupId, String search, Pageable pageable);

    void removeMemberFromGroup(AddGroupMemberDto removeGroupMemberDto);
}
