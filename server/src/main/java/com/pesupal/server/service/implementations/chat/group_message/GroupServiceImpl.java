package com.pesupal.server.service.implementations.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.CreateGroupDto;
import com.pesupal.server.dto.request.chat.group_message.UpdateGroupChatConfigurationDto;
import com.pesupal.server.dto.response.chat.ChatPreviewDto;
import com.pesupal.server.dto.response.chat.LastMessageDto;
import com.pesupal.server.dto.response.chat.RecentChatDto;
import com.pesupal.server.dto.response.chat.RecentChatPagedDto;
import com.pesupal.server.dto.response.chat.group_message.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.enums.Visibility;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.DateTimeUtil;
import com.pesupal.server.helpers.GroupHelper;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatConfiguration;
import com.pesupal.server.model.chat.group_message.GroupChatMember;
import com.pesupal.server.model.chat.group_message.GroupChatPinned;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.RecentGroupChatProjection;
import com.pesupal.server.repository.chat.group_message.GroupChatMemberRepository;
import com.pesupal.server.repository.chat.group_message.GroupRepository;
import com.pesupal.server.service.interfaces.MediaService;
import com.pesupal.server.service.interfaces.UserService;
import com.pesupal.server.service.interfaces.chat.group_message.*;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceImpl extends CurrentValueRetriever implements GroupService {

    private final UserService userService;
    private final MediaService mediaService;
    private final GroupRepository groupRepository;
    private final OrgMemberService orgMemberService;
    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatPinnedService groupchatPinnedService;
    private final GroupChatMessageService groupChatMessageService;
    private final GroupChatMemberRepository groupChatMemberRepository;
    private final GroupChatConfigurationService groupChatConfigurationService;

    public GroupServiceImpl(UserService userService, GroupRepository groupRepository, OrgMemberService orgMemberService, GroupChatMemberService groupChatMemberService, GroupChatPinnedService groupchatPinnedService, @Lazy GroupChatMessageService groupChatMessageService, GroupChatMemberRepository groupChatMemberRepository, GroupChatConfigurationService groupChatConfigurationService, MediaService mediaService) {
        this.userService = userService;
        this.mediaService = mediaService;
        this.groupRepository = groupRepository;
        this.orgMemberService = orgMemberService;
        this.groupChatMemberService = groupChatMemberService;
        this.groupchatPinnedService = groupchatPinnedService;
        this.groupChatMessageService = groupChatMessageService;
        this.groupChatMemberRepository = groupChatMemberRepository;
        this.groupChatConfigurationService = groupChatConfigurationService;
    }

    /**
     * Initializes the group chat member for a given group and organization member.
     *
     * @param group
     * @param orgMember
     */
    private void initializeGroupChatMember(Group group, OrgMember orgMember) {

        GroupChatMember groupChatMember = new GroupChatMember();
        groupChatMember.setGroup(group);
        groupChatMember.setParticipant(orgMember);
        groupChatMember.setActive(true);
        groupChatMember.setRole(Role.SUPER_ADMIN);
        groupChatMemberRepository.save(groupChatMember);
    }

    /**
     * Creates a new group based on the provided CreateGroupDto.
     *
     * @param createGroupDto
     * @return
     */
    @Override
    @Transactional
    public GroupDto createGroup(CreateGroupDto createGroupDto) {

        OrgMember owner = getCurrentOrgMember();
        Group group = createGroupDto.toGroup();
        group.setOwner(owner);
        group.setOrg(owner.getOrg());
        groupRepository.save(group);
        groupChatConfigurationService.initializeGroupChatConfiguration(group);
        initializeGroupChatMember(group, owner);
        groupChatMessageService.addSystemMessage(group, owner.getDisplayName() + " has created the group.");
        URL displayPicture = null;
        try {
            displayPicture = mediaService.generatePresignedUrl(createGroupDto.getDisplayPicture());
        } catch (Exception ignored) {
        }
        return GroupDto.fromGroupAndOrgMemberAndDisplayPicture(group, owner, displayPicture);
    }

    /**
     * @param groupId
     * @return
     */
    @Override
    public Group getGroupByPublicId(String groupId) {

        return groupRepository.findByPublicId(groupId).orElseThrow(() -> new DataNotFoundException("Group not found."));
    }

    /**
     * Deletes a group based on the provided group ID, user ID, and organization ID.
     *
     * @param groupId
     */
    @Override
    public void deleteGroup(String groupId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getId();

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, userId);
        Group group = getGroupByPublicId(groupId);

        Role role = groupChatMember.getRole();

        if (!group.getOwner().getId().equals(userId)) {
            GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, role);
            if (!groupChatConfiguration.isDeleteGroup()) {
                throw new PermissionDeniedException("You do not have permission to delete this group.");
            }
        }

        if (!group.isActive()) {
            throw new ActionProhibitedException("This group is no longer active.");
        }

        group.setActive(false);
        groupRepository.save(group);
        groupChatMessageService.addSystemMessage(group, orgMember.getDisplayName() + " has deleted the group.");
    }

    /**
     * Retrieves all groups for a user in a specific organization.
     *
     * @return
     */
    @Override
    public RecentChatPagedDto getRecentGroups(String search, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        OrgMember orgMember = getCurrentOrgMember();
        Long orgMemberId = orgMember.getId();

        Org org = orgMember.getOrg();
        Long orgId = org.getId();

        List<RecentGroupChatProjection> rows = groupRepository.findRecentGroupChatsPaged(orgMemberId, orgId, search, size, offset);

        List<RecentChatDto> chats = rows.stream().map(proj -> {
            LastMessageDto lastMessage = new LastMessageDto();
            lastMessage.setSender(proj.getSenderName());
            lastMessage.setMessage(proj.getContent());
            lastMessage.setMedia(proj.getIncludedMedia());
            lastMessage.setCreatedAt(DateTimeUtil.formatShort(proj.getCreatedAt()));
            lastMessage.setMessageStatus(proj.getMessageStatus());
            lastMessage.setMessageType(proj.getMessageType());

            RecentChatDto dto = new RecentChatDto();
            dto.setChatId(proj.getGroupId());
            dto.setName(proj.getGroupName());
            try {
                dto.setImage(mediaService.generatePresignedUrl(proj.getSenderDisplayPicture()));
            } catch (Exception ignored) {
            }
            dto.setStatus(proj.getGroupVisibility());
            dto.setRecentMessage(lastMessage);

            return dto;
        }).toList();

        Long total = groupRepository.countRecentGroupChats(orgMemberId, orgId);

        return new RecentChatPagedDto(chats, pageable, total);
    }

    /**
     * Retrieves a group chat by its ID, user ID, and organization ID.
     *
     * @param groupId
     * @return
     */
    @Override
    public ChatPreviewDto getGroupChatPreviewByChatId(String groupId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        Long userId = orgMember.getId();
        Group group = getGroupByPublicId(groupId);
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not exist");
        }

        Optional<GroupChatMember> optionalGroupChatMember = group.getMembers().stream().filter(gcm -> gcm.getParticipant().getId().equals(userId)).findFirst();
        boolean isActiveGroupMember;
        if (optionalGroupChatMember.isEmpty()) {
            if (!group.getVisibility().equals(Visibility.PUBLIC)) {
                throw new PermissionDeniedException("You do not have permission to access this chat.");
            }
            isActiveGroupMember = true;
        } else {
            isActiveGroupMember = optionalGroupChatMember.get().isActive();
        }

        if (!group.getVisibility().equals(Visibility.PUBLIC) && !isActiveGroupMember && !group.isInactiveMemberAccessChat()) {
            throw new PermissionDeniedException("You don't have permission to access this chat.");
        }

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, userId);
        GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, groupChatMember.getRole());

        boolean groupActive = group.isActive();
        ChatPreviewDto chatPreviewDto = new ChatPreviewDto();
        chatPreviewDto.setChatId(groupId);
        chatPreviewDto.setGroupActive(groupActive);
        chatPreviewDto.setReopenable(!groupActive && GroupHelper.isGroupOwner(group, orgMember));
        chatPreviewDto.setActive(isActiveGroupMember && optionalGroupChatMember.isPresent());
        chatPreviewDto.setDisplayName(group.getName());
        chatPreviewDto.setVisibility(group.getVisibility());
        chatPreviewDto.setDescription(group.getDescription());
        try {
            chatPreviewDto.setDisplayPicture(mediaService.generatePresignedUrl(group.getDisplayPicture()));
        } catch (Exception ignored) {
        }
        chatPreviewDto.setGroupChatConfiguration(UpdateGroupChatConfigurationDto.fromGroupChatConfiguration(groupChatConfiguration));
        chatPreviewDto.setParticipantsCount(group.getMembers().stream().filter(GroupChatMember::isActive).toList().size());
        Optional<GroupChatPinned> pinnedGroupChat = groupchatPinnedService.getPinnedGroupByPinnedByAndGroup(orgMember, group);
        pinnedGroupChat.ifPresent(groupChatPinned -> chatPreviewDto.setPinnedId(groupChatPinned.getId()));
        return chatPreviewDto;
    }

    /**
     * Reopens a group by its ID if the current user is the owner of the group.
     *
     * @param groupId
     */
    @Override
    public void reopenGroup(String groupId) {

        OrgMember orgMember = getCurrentOrgMember();

        Group group = getGroupByPublicId(groupId);
        if (!GroupHelper.isGroupOwner(group, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to reopen this group.");
        }

        if (group.isActive()) {
            throw new ActionProhibitedException("This group is already active.");
        }

        group.setActive(true);
        groupRepository.save(group);
        // initializeGroupChatMember(group, orgMember);
        groupChatMessageService.addSystemMessage(group, orgMember.getDisplayName() + " has reopened the group.");
    }

    /**
     * Updates an existing group with the provided CreateGroupDto.
     *
     * @param groupId
     * @param updateGroupDto
     * @return
     */
    @Override
    public GroupDto updateGroup(String groupId, CreateGroupDto updateGroupDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Group group = getGroupByPublicId(groupId);

        if (!GroupHelper.isGroupOwner(group, orgMember)) {
            throw new PermissionDeniedException("You do not have permission to update this group.");
        }

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, orgMember.getId());
        if (!groupChatMember.isActive()) {
            throw new PermissionDeniedException("You do not have permission to update this group as you are not an active member.");
        }

        GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, groupChatMember.getRole());

        if (!updateGroupDto.getName().equals(group.getName()) && !groupChatConfiguration.isChangeName()) {
            throw new PermissionDeniedException("You do not have permission to update the group name.");
        }

        if (!updateGroupDto.getDescription().equals(group.getDescription()) && !groupChatConfiguration.isChangeDescription()) {
            throw new PermissionDeniedException("You do not have permission to update the group description.");
        }

        if (!groupChatConfiguration.isChangeProfilePicture()) {
            if (updateGroupDto.getDisplayPicture() == null && group.getDisplayPicture() != null) {
                throw new PermissionDeniedException("You do not have permission to update the group display picture.");
            }
            if (updateGroupDto.getDisplayPicture() != null && !updateGroupDto.getDisplayPicture().equals(group.getDisplayPicture())) {
                throw new PermissionDeniedException("You do not have permission to update the group display picture.");
            }
        }

        if (!updateGroupDto.getVisibility().equals(group.getVisibility()) && !groupChatConfiguration.isChangeVisibility()) {
            throw new PermissionDeniedException("You do not have permission to update the group visibility.");
        }

        UUID previousDisplayPicture = group.getDisplayPicture();

        updateGroupDto.applyToGroup(group);

        UUID newDisplayPicture = group.getDisplayPicture();

        if (group.getName().isBlank()) {
            throw new PermissionDeniedException("Group name cannot be empty.");
        }

        if (group.getVisibility() == null) {
            throw new PermissionDeniedException("Group visibility cannot be null.");
        }

        groupRepository.save(group);

        if (newDisplayPicture == null) {
            if (previousDisplayPicture != null) {
                mediaService.deleteFile(previousDisplayPicture);
            }
        } else {
            if (previousDisplayPicture != null && !previousDisplayPicture.equals(newDisplayPicture)) {
                mediaService.deleteFile(previousDisplayPicture);
            }
        }

        GroupDto groupDto = GroupDto.fromGroup(group);
        try {
            groupDto.setDisplayPicture(mediaService.generatePresignedUrl(group.getDisplayPicture()));
        } catch (Exception ignored) {
        }
        return groupDto;
    }
}
