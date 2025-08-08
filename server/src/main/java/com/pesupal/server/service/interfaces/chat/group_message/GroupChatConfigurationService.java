package com.pesupal.server.service.interfaces.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.UpdateGroupChatConfigurationDto;
import com.pesupal.server.dto.response.chat.group_message.GroupChatPermissionDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatConfiguration;

import java.util.List;

public interface GroupChatConfigurationService {

    void initializeGroupChatConfiguration(Group group);

    GroupChatConfiguration getConfigurationByGroupAndRole(Group group, Role role);

    void updateGroupChatConfiguration(UpdateGroupChatConfigurationDto updateGroupChatConfigurationDto);

    List<GroupChatConfiguration> getConfigurationsByGroup(Group group);

    List<GroupChatPermissionDto> getGroupPermissions(String groupId);
}
