package com.pesupal.server.service.implementations.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.AddGroupMemberDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.chat.group_message.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.enums.Visibility;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatConfiguration;
import com.pesupal.server.model.chat.group_message.GroupChatMember;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.chat.group_message.GroupChatMemberRepository;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatConfigurationService;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatMemberService;
import com.pesupal.server.service.interfaces.chat.group_message.GroupService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupChatMemberServiceImpl extends CurrentValueRetriever implements GroupChatMemberService {

    private final GroupService groupService;
    private final OrgMemberService orgMemberService;
    private final GroupChatMessageServiceImpl groupChatMessageService;
    private final GroupChatMemberRepository groupChatMemberRepository;
    private final GroupChatConfigurationService groupChatConfigurationService;

    public GroupChatMemberServiceImpl(@Lazy GroupService groupService, OrgMemberService orgMemberService, GroupChatMemberRepository groupChatMemberRepository, GroupChatConfigurationService groupChatConfigurationService, @Lazy GroupChatMessageServiceImpl groupChatMessageService) {
        this.groupService = groupService;
        this.orgMemberService = orgMemberService;
        this.groupChatMessageService = groupChatMessageService;
        this.groupChatMemberRepository = groupChatMemberRepository;
        this.groupChatConfigurationService = groupChatConfigurationService;
    }

    /**
     * Retrieves a group chat member by group ID, user ID, and organization ID.
     *
     * @param groupId
     * @param userId
     * @return
     */
    @Override
    public GroupChatMember getGroupMemberByGroupIdAndUserId(String groupId, Long userId) {

        return groupChatMemberRepository.findByGroup_PublicIdAndParticipantId(groupId, userId).orElseThrow(() -> new DataNotFoundException("User with ID " + userId + " is not a member of this group."));
    }

    /**
     * Retrieves a group chat member by group ID, user ID, and organization ID.
     *
     * @param groupId
     * @param userId
     * @return
     */
    @Override
    public GroupChatMember getGroupMemberByGroupIdAndUserId(Long groupId, Long userId) {

        return groupChatMemberRepository.findByGroupIdAndParticipantId(groupId, userId).orElseThrow(() -> new DataNotFoundException("User with ID " + userId + " is not a member of this group."));
    }

    /**
     * Allows a user to join a group by group ID.
     *
     * @param groupId
     * @return
     */
    @Override
    public GroupDto joinGroup(String groupId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        Long userId = orgMember.getId();

        Group group = groupService.getGroupByPublicId(groupId);
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not belong to organization with ID " + orgId + ".");
        }

        Optional<GroupChatMember> optionalGroupChatMember = group.getMembers().stream().filter(gcm -> gcm.getParticipant().getId().equals(userId)).findFirst();
        GroupChatMember groupChatMember;
        if (optionalGroupChatMember.isEmpty()) {
            groupChatMember = new GroupChatMember();
            groupChatMember.setRole(Role.USER);
            groupChatMember.setParticipant(orgMember);
            groupChatMember.setGroup(group);
        } else {
            groupChatMember = optionalGroupChatMember.get();
            if (groupChatMember.isActive()) {
                throw new ActionProhibitedException("You are already a member of this group.");
            }
        }

        if (group.getVisibility().equals(Visibility.PRIVATE)) {
            throw new PermissionDeniedException("The group that you are trying to join is private. Please contact the group owner for access.");
        }

        if (!group.isActive()) {
            throw new ActionProhibitedException("The group you are trying to join is no longer active.");
        }

        // groupChatMember.setLastReadMessage(latestMessage);
        groupChatMember.setActive(true);
        groupChatMemberRepository.save(groupChatMember);

        groupChatMessageService.addSystemMessage(group, orgMember.getDisplayName() + " has joined the group.");

        return GroupDto.fromGroup(group);
    }


    /**
     * Allows a user to leave a group by group ID.
     *
     * @param groupId
     */
    @Override
    public void leaveGroup(String groupId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getId();
        Long orgId = orgMember.getOrg().getId();

        GroupChatMember groupChatMember = getGroupMemberByGroupIdAndUserId(groupId, userId);
        Group group = groupChatMember.getGroup();
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not exist.");
        }

        Role role = groupChatMember.getRole();
        GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, role);
        if (!groupChatConfiguration.isLeaveGroup()) {
            throw new PermissionDeniedException("You do not have permission to leave from this group.");
        }

        groupChatMember.setActive(false);
        groupChatMemberRepository.save(groupChatMember);

        groupChatMessageService.addSystemMessage(group, orgMember.getDisplayName() + " has left the group.");
    }

    /**
     * Retrieves a list of users who are not participants of a group, optionally filtered by search criteria.
     *
     * @param groupId
     * @param search
     * @param pageable
     * @return
     */
    @Override
    public List<UserPreviewDto> getNonParticipantMembers(String groupId, String search, Pageable pageable) {

        OrgMember orgMember = getCurrentOrgMember();
        GroupChatMember groupChatMember = getGroupMemberByGroupIdAndUserId(groupId, orgMember.getId());

        return groupChatMemberRepository.getNonParticipantMembersByGroupId(groupChatMember.getGroup().getPublicId(), search, pageable).getContent().stream().map(UserPreviewDto::fromOrgMember).toList();
    }

    /**
     * Removes a member from a group.
     *
     * @param removeGroupMemberDto
     */
    @Override
    public void removeMemberFromGroup(AddGroupMemberDto removeGroupMemberDto) {

        OrgMember orgMember = getCurrentOrgMember();
        GroupChatMember groupChatMember = getGroupMemberByGroupIdAndUserId(removeGroupMemberDto.getGroupId(), orgMember.getId());
        Role role = groupChatMember.getRole();

        Group group = groupService.getGroupByPublicId(removeGroupMemberDto.getGroupId());
        OrgMember memberToRemove = orgMemberService.getOrgMemberByPublicId(removeGroupMemberDto.getUserId());

        GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, groupChatMember.getRole());

        if (!groupChatConfiguration.isRemoveMember()) {
            throw new PermissionDeniedException("You do not have permission to remove members from this group.");
        }

        if (orgMember.getPublicId().equals(memberToRemove.getPublicId())) {
            throw new ActionProhibitedException("You cannot remove yourself from the group.");
        }

        if (group.getOwner().getPublicId().equals(memberToRemove.getPublicId())) {
            throw new ActionProhibitedException("You cannot remove the group owner from the group.");
        }

        GroupChatMember memberToRemoveChatMember = getGroupMemberByGroupIdAndUserId(removeGroupMemberDto.getGroupId(), memberToRemove.getId());
        Role memberToRemoveRole = memberToRemoveChatMember.getRole();

        if (role.getLevel() < memberToRemoveRole.getLevel()) {
            throw new PermissionDeniedException("The role of the member you are trying to remove is higher than yours. You do not have permission to remove this member.");
        }

        memberToRemoveChatMember.setActive(false);
        groupChatMemberRepository.save(memberToRemoveChatMember);
    }

    /**
     * Adds a member to a group.
     *
     * @param addGroupMemberDto
     * @return
     */
    @Override
    public UserPreviewDto addMemberToGroup(AddGroupMemberDto addGroupMemberDto) {

        OrgMember currentUser = getCurrentOrgMember();
        Long userId = currentUser.getId();
        Long orgId = currentUser.getOrg().getId();

        GroupChatMember groupChatMember = getGroupMemberByGroupIdAndUserId(addGroupMemberDto.getGroupId(), userId);
        Group group = groupChatMember.getGroup();
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + addGroupMemberDto.getGroupId() + " does not exist.");
        }

        Role role = groupChatMember.getRole();
        GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, role);
        if (!groupChatConfiguration.isAddMember()) {
            throw new PermissionDeniedException("You do not have permission to add members to this group.");
        }

        boolean isAlreadyMember = groupChatMemberRepository.existsByGroup_PublicIdAndParticipant_PublicId(addGroupMemberDto.getGroupId(), addGroupMemberDto.getUserId());
        if (isAlreadyMember) {
            throw new ActionProhibitedException("User is already a member of this group.");
        }

        OrgMember newMember = orgMemberService.getOrgMemberByPublicId(addGroupMemberDto.getUserId());

        GroupChatMember newGroupMember = new GroupChatMember();
        newGroupMember.setGroup(group);
        newGroupMember.setParticipant(newMember);
        newGroupMember.setRole(Role.USER);
        newGroupMember.setActive(true);
        groupChatMemberRepository.save(newGroupMember);
        return UserPreviewDto.fromOrgMember(newMember);
    }

    /**
     * Retrieves the members of a group categorized by their roles.
     *
     * @param groupId
     * @return
     */
    @Override
    public Map<Role, List<UserPreviewDto>> getGroupMembers(String groupId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getId();
        Long orgId = orgMember.getOrg().getId();

        GroupChatMember groupChatMember = getGroupMemberByGroupIdAndUserId(groupId, userId);
        Group group = groupChatMember.getGroup();
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not exist.");
        }

        Role role = groupChatMember.getRole();
        GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, role);
        if (!groupChatConfiguration.isViewMembers()) {
            throw new PermissionDeniedException("You do not have permission to view members of this group.");
        }

        return group.getMembers().stream().filter(GroupChatMember::isActive).collect(Collectors.groupingBy(
                GroupChatMember::getRole,
                Collectors.mapping(
                        member -> UserPreviewDto.fromOrgMember(orgMemberService.getOrgMemberByUserIdAndOrgId(member.getParticipant().getId(), orgId)),
                        Collectors.toList()
                )
        )).entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, e -> e.getValue().stream().sorted(Comparator.comparing(UserPreviewDto::getDisplayName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList())
        ));

    }

}
