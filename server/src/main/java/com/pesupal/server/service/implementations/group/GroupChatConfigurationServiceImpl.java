package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.group.UpdateGroupChatConfigurationDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatConfiguration;
import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.GroupChatConfigurationRepository;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import com.pesupal.server.service.interfaces.group.GroupChatMemberService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class GroupChatConfigurationServiceImpl extends CurrentValueRetriever implements GroupChatConfigurationService {

    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatConfigurationRepository groupChatConfigurationRepository;

    public GroupChatConfigurationServiceImpl(@Lazy GroupChatMemberService groupChatMemberService, GroupChatConfigurationRepository groupChatConfigurationRepository) {
        this.groupChatMemberService = groupChatMemberService;
        this.groupChatConfigurationRepository = groupChatConfigurationRepository;
    }

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

    /**
     * Updates the group chat configuration for a specific group, role, user, and organization.
     *
     * @param updateGroupChatConfigurationDto
     * @return
     */
    @Override
    public void updateGroupChatConfiguration(UpdateGroupChatConfigurationDto updateGroupChatConfigurationDto) {

        Long groupId = updateGroupChatConfigurationDto.getGroupId();
        Role role = updateGroupChatConfigurationDto.getRole();

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        Long userId = orgMember.getUser().getId();

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, userId);
        Group group = groupChatMember.getGroup();
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not exist.");
        }

        Role userRole = groupChatMember.getRole();

        if (!userRole.equals(Role.SUPER_ADMIN)) {
            throw new PermissionDeniedException("You do not have permission to update the group chat configuration.");
        }

        GroupChatConfiguration groupChatConfiguration = getConfigurationByGroupAndRole(group, role);
        updateGroupChatConfigurationDto.applyToGroupChatConfiguration(groupChatConfiguration);
        groupChatConfigurationRepository.save(groupChatConfiguration);
    }
}
