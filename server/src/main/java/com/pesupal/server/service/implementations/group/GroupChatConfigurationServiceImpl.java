package com.pesupal.server.service.implementations.group;

import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatConfiguration;
import com.pesupal.server.repository.GroupChatConfigurationRepository;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupChatConfigurationServiceImpl implements GroupChatConfigurationService {

    private final GroupChatConfigurationRepository groupChatConfigurationRepository;

    /**
     * Initializes the default configuration of group chat for a given group.
     *
     * @param group
     */
    @Override
    public void initializeGroupChatConfiguration(Group group) {

        for (Role role : Role.values()) {
            GroupChatConfiguration groupChatConfiguration = GroupChatConfiguration.builder().group(group).role(role).addMember(true).removeMember(true).changeName(true).changeDescription(true).deleteGroup(true).leaveGroup(true).changeProfilePicture(true).viewMembers(true).postMessage(true).pinMessage(true).deleteMessage(true).clearChat(true).roleUpdate(true).build();
            switch (role) {
                case SUPER_ADMIN: {
                    break;  // Super Admin has all permissions by default
                }
                case USER: {
                    groupChatConfiguration.setAddMember(false);
                    groupChatConfiguration.setLeaveGroup(false);
                    groupChatConfiguration.setViewMembers(false);
                    groupChatConfiguration.setPinMessage(false);
                    groupChatConfiguration.setDeleteMessage(false);
                    groupChatConfiguration.setRoleUpdate(false);
                }
                case ADMIN: {
                    groupChatConfiguration.setDeleteGroup(false);
                    groupChatConfiguration.setClearChat(false);
                    groupChatConfiguration.setChangeName(false);
                    groupChatConfiguration.setRemoveMember(false);
                    groupChatConfiguration.setChangeDescription(false);
                    groupChatConfiguration.setChangeProfilePicture(false);
                    break;
                }
            }
            groupChatConfigurationRepository.save(groupChatConfiguration);
        }
    }

    /**
     * Retrieves the group chat configuration for a specific group and role.
     *
     * @param group
     * @param role
     * @return
     */
    @Override
    public GroupChatConfiguration getConfigurationByGroupAndRole(Group group, Role role) {

        return groupChatConfigurationRepository.findByGroupAndRole(group, role).orElseThrow(() -> new DataNotFoundException("Configuration not found for group: " + group.getId() + " and role: " + role));
    }
}
