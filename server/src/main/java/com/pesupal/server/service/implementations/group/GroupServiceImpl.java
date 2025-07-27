package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.ChatPreviewDto;
import com.pesupal.server.dto.response.LastMessageDto;
import com.pesupal.server.dto.response.RecentChatDto;
import com.pesupal.server.dto.response.RecentChatPagedDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.TimeFormatterUtil;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatConfiguration;
import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.projections.RecentGroupChatProjection;
import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.repository.GroupRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.UserService;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import com.pesupal.server.service.interfaces.group.GroupChatMemberService;
import com.pesupal.server.service.interfaces.group.GroupChatPinnedService;
import com.pesupal.server.service.interfaces.group.GroupService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupServiceImpl extends CurrentValueRetriever implements GroupService {

    private final UserService userService;
    private final GroupRepository groupRepository;
    private final OrgMemberService orgMemberService;
    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatMemberRepository groupChatMemberRepository;
    private final GroupChatPinnedService groupchatPinnedService;
    private final GroupChatConfigurationService groupChatConfigurationService;

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
        return GroupDto.fromGroupAndOrgMember(group, owner);
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

        GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, role);
        if (!groupChatConfiguration.isDeleteGroup()) {
            throw new PermissionDeniedException("You do not have permission to delete this group.");
        }

        if (!group.isActive()) {
            throw new ActionProhibitedException("This group is no longer active.");
        }

        group.setActive(false);
        groupRepository.save(group);
    }

    /**
     * Retrieves all groups for a user in a specific organization.
     *
     * @return
     */
    @Override
    public RecentChatPagedDto getAllGroups(Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getId();

        User user = userService.getUserById(userId);
        Org org = orgMember.getOrg();
        Long orgId = org.getId();

        orgMemberService.validateUserIsOrgMember(user, org);

        List<RecentGroupChatProjection> rows = groupRepository.findRecentGroupChatsPaged(userId, orgId, size, offset);

        List<RecentChatDto> chats = rows.stream().map(proj -> {
            LastMessageDto lastMessage = new LastMessageDto();
            lastMessage.setSender(proj.getSenderName());
            lastMessage.setMessage(proj.getContent());
            lastMessage.setMedia(proj.getIncludedMedia());
            lastMessage.setCreatedAt(TimeFormatterUtil.formatShort(proj.getCreatedAt()));

            RecentChatDto dto = new RecentChatDto();
            dto.setChatId(String.valueOf(proj.getGroupId()));
            dto.setName(proj.getGroupName());
            dto.setImage(proj.getSenderDisplayPicture());
            dto.setStatus(proj.getGroupVisibility());
            dto.setRecentMessage(lastMessage);

            return dto;
        }).toList();

        Long total = groupRepository.countRecentGroupChats(userId, orgId);

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

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, userId);
        if (!groupChatMember.isActive() && !group.isInactiveMemberAccessChat()) {
            throw new PermissionDeniedException("You don't have permission to access this chat.");
        }

        ChatPreviewDto chatPreviewDto = new ChatPreviewDto();
        chatPreviewDto.setChatId(groupId);
        chatPreviewDto.setActive(groupChatMember.isActive());
        chatPreviewDto.setDisplayName(group.getName());
        chatPreviewDto.setGroupActive(group.isActive());
        chatPreviewDto.setDisplayPicture(group.getDisplayPicture());
        chatPreviewDto.setParticipantsCount(group.getMembers().stream().filter(GroupChatMember::isActive).toList().size());
        Optional<GroupChatPinned> pinnedGroupChat = groupchatPinnedService.getPinnedGroupByPinnedByAndGroup(orgMember, group);
        if (pinnedGroupChat.isPresent()) {
            chatPreviewDto.setPinnedId(pinnedGroupChat.get().getId());
        }
        return chatPreviewDto;
    }
}
