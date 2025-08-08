package com.pesupal.server.service.implementations.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.UpdateGroupChatConfigurationDto;
import com.pesupal.server.dto.response.chat.group_message.GroupChatPermissionDto;
import com.pesupal.server.dto.response.chat.group_message.GroupConfigurationUpdateDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.GroupHelper;
import com.pesupal.server.helpers.StringHelper;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatConfiguration;
import com.pesupal.server.model.chat.group_message.GroupChatMember;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.chat.group_message.GroupChatConfigurationRepository;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatConfigurationService;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatMemberService;
import com.pesupal.server.service.interfaces.chat.group_message.GroupService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupChatConfigurationServiceImpl extends CurrentValueRetriever implements GroupChatConfigurationService {

    private final GroupService groupService;
    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatConfigurationRepository groupChatConfigurationRepository;

    public GroupChatConfigurationServiceImpl(@Lazy GroupChatMemberService groupChatMemberService, GroupChatConfigurationRepository groupChatConfigurationRepository, @Lazy GroupService groupService) {
        this.groupService = groupService;
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

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        Long userId = orgMember.getUser().getId();

        Long groupId = updateGroupChatConfigurationDto.getGroupId();
        Role role = updateGroupChatConfigurationDto.getRole();

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

    /**
     * Updates the group chat configuration based on the provided DTO.
     *
     * @param groupConfigurationUpdateDto
     */
    @Override
    public void updateGroupChatConfiguration(GroupConfigurationUpdateDto groupConfigurationUpdateDto) throws IllegalAccessException {

        OrgMember orgMember = getCurrentOrgMember();
        Group group = groupService.getGroupByPublicId(groupConfigurationUpdateDto.getGroupId());

        if (!GroupHelper.isGroupOwner(group, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to update this group configuration.");
        }

        String fieldName = StringHelper.toCamelCase(groupConfigurationUpdateDto.getName());
        boolean isEnabled = groupConfigurationUpdateDto.isEnable();
        Role role = groupConfigurationUpdateDto.getRole();

        GroupChatConfiguration groupChatConfiguration = getConfigurationByGroupAndRole(group, role);

        for (Field field : GroupChatConfiguration.class.getDeclaredFields()) {
            if (field.getType().equals(boolean.class) && field.getName().equals(fieldName)) {
                field.setAccessible(true); // allow access to private fields
                field.setBoolean(groupChatConfiguration, isEnabled);
                groupChatConfigurationRepository.save(groupChatConfiguration);
                return;
            }
        }

        throw new DataNotFoundException("Field " + fieldName + " not found in GroupChatConfiguration.");
    }

    /**
     * Retrieves all group chat configurations for a specific group.
     *
     * @param group
     * @return
     */
    @Override
    public List<GroupChatConfiguration> getConfigurationsByGroup(Group group) {

        return groupChatConfigurationRepository.findAllByGroup(group);
    }

    /**
     * Retrieves the permissions for a group by its ID.
     *
     * @param groupId
     * @return
     */
    @Override
    public List<GroupChatPermissionDto> getGroupPermissions(String groupId) {

        OrgMember orgMember = getCurrentOrgMember();
        Group group = groupService.getGroupByPublicId(groupId);
        if (!GroupHelper.isGroupOwner(group, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to access this group.");
        }

        List<GroupChatConfiguration> configurations = getConfigurationsByGroup(group);
        Map<String, GroupChatPermissionDto> permissions = new HashMap<>();
        for (GroupChatConfiguration configuration : configurations) {
            Role role = configuration.getRole();
            for (Field field : GroupChatConfiguration.class.getDeclaredFields()) {
                if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                    field.setAccessible(true); // allow access to private fields
                    try {
                        String fieldName = StringHelper.camelCaseToTitle(field.getName());
                        boolean fieldValue = field.getBoolean(configuration);
                        GroupChatPermissionDto groupChatPermissionDto = permissions.computeIfAbsent(fieldName, k -> new GroupChatPermissionDto(fieldName));
                        switch (role) {
                            case SUPER_ADMIN -> {
                                groupChatPermissionDto.setSuperAdmin(fieldValue);
                            }
                            case ADMIN -> {
                                groupChatPermissionDto.setAdmin(fieldValue);
                            }
                            case USER -> {
                                groupChatPermissionDto.setUser(fieldValue);
                            }
                        }
                    } catch (IllegalAccessException ignored) {
                    }
                }
            }
        }

        List<GroupChatPermissionDto> permissionDtos = new ArrayList<>();
        for (Map.Entry<String, GroupChatPermissionDto> entry : permissions.entrySet()) {
            permissionDtos.add(entry.getValue());
        }
        return permissionDtos;
    }

}
