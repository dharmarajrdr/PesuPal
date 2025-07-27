package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.group.AddGroupMemberDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.enums.Visibility;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatConfiguration;
import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import com.pesupal.server.service.interfaces.group.GroupChatMemberService;
import com.pesupal.server.service.interfaces.group.GroupService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GroupChatMemberServiceImpl extends CurrentValueRetriever implements GroupChatMemberService {

    private final GroupService groupService;
    private final OrgMemberService orgMemberService;
    private final GroupChatMemberRepository groupChatMemberRepository;
    private final GroupChatConfigurationService groupChatConfigurationService;

    public GroupChatMemberServiceImpl(@Lazy GroupService groupService, OrgMemberService orgMemberService, GroupChatMemberRepository groupChatMemberRepository, GroupChatConfigurationService groupChatConfigurationService) {
        this.groupService = groupService;
        this.orgMemberService = orgMemberService;
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

        return groupChatMemberRepository.findByGroup_PublicIdAndParticipantId(groupId, userId).orElseThrow(() -> new DataNotFoundException("User with ID " + userId + " is not a member of group with ID " + groupId + "."));
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

        return groupChatMemberRepository.findByGroupIdAndParticipantId(groupId, userId).orElseThrow(() -> new DataNotFoundException("User with ID " + userId + " is not a member of group with ID " + groupId + "."));
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

        Group group = groupService.getGroupByPublicId(groupId);
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not belong to organization with ID " + orgId + ".");
        }

        boolean alreadyMember = groupChatMemberRepository.existsByGroup_PublicIdAndParticipant_PublicId(groupId, orgMember.getPublicId());
        if (alreadyMember) {
            throw new ActionProhibitedException("You are already a member of this group.");
        }

        if (group.getVisibility().equals(Visibility.PRIVATE)) {
            throw new PermissionDeniedException("The group that you are trying to join is private. Please contact the group owner for access.");
        }

        GroupChatMember groupChatMember = new GroupChatMember();
        groupChatMember.setRole(Role.USER);
        groupChatMember.setActive(true);
        groupChatMember.setParticipant(orgMember);
        groupChatMember.setGroup(group);
        // groupChatMember.setLastReadMessage(latestMessage);
        groupChatMemberRepository.save(groupChatMember);
        return GroupDto.fromGroup(group);
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
                        member -> UserPreviewDto.fromOrgMember(orgMemberService.getOrgMemberByUserIdAndOrgId(member.getId(), orgId)),
                        Collectors.toList()
                )
        )).entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, e -> e.getValue().stream().sorted(Comparator.comparing(UserPreviewDto::getDisplayName, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList())
        ));

    }

    /**
     * Checks if a user is a member of a group.
     *
     * @param groupId
     * @return
     */
    @Override
    public void checkUserPartOfGroup(String groupId) {

        OrgMember orgMember = getCurrentOrgMember();
        GroupChatMember groupChatMember = getGroupMemberByGroupIdAndUserId(groupId, orgMember.getId());
        if (!groupChatMember.isActive()) {
            throw new PermissionDeniedException("You are no longer a member of this group.");
        }
    }

}
