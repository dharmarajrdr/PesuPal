package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.UpdateGroupChatConfigurationDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatConfiguration;

public interface GroupChatConfigurationService {

    void initializeGroupChatConfiguration(Group group);

    GroupChatConfiguration getConfigurationByGroupAndRole(Group group, Role role);

    void updateGroupChatConfiguration(UpdateGroupChatConfigurationDto updateGroupChatConfigurationDto, Long userId, Long orgId);
}
