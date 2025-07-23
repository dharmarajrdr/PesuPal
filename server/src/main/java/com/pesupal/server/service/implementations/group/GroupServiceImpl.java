package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.group.CreateGroupDto;
import com.pesupal.server.dto.response.ChatPreviewDto;
import com.pesupal.server.dto.response.LastMessageDto;
import com.pesupal.server.dto.response.RecentChatDto;
import com.pesupal.server.dto.response.RecentChatPagedDto;
import com.pesupal.server.dto.response.group.GroupDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.enums.Visibility;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.TimeFormatterUtil;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatConfiguration;
import com.pesupal.server.model.group.GroupChatMember;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.repository.GroupRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.OrgService;
import com.pesupal.server.service.interfaces.UserService;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import com.pesupal.server.service.interfaces.group.GroupChatMemberService;
import com.pesupal.server.service.interfaces.group.GroupChatPinnedService;
import com.pesupal.server.service.interfaces.group.GroupService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final OrgService orgService;
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
        groupChatMember.setUser(orgMember.getUser());
        groupChatMember.setActive(true);
        groupChatMember.setRole(Role.SUPER_ADMIN);
        groupChatMemberRepository.save(groupChatMember);
    }

    /**
     * Creates a new group based on the provided CreateGroupDto.
     *
     * @param createGroupDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    @Transactional
    public GroupDto createGroup(CreateGroupDto createGroupDto, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        Group group = createGroupDto.toGroup();
        group.setOwner(orgMember.getUser());
        group.setOrg(orgMember.getOrg());
        groupRepository.save(group);
        groupChatConfigurationService.initializeGroupChatConfiguration(group);
        initializeGroupChatMember(group, orgMember);
        return GroupDto.fromGroupAndOrgMember(group, orgMember);
    }

    /**
     * Retrieves a group by its ID and organization ID.
     *
     * @param groupId
     * @return
     */
    @Override
    public Group getGroupById(Long groupId) {

        return groupRepository.findById(groupId).orElseThrow(() -> new DataNotFoundException("Group with ID " + groupId + " not found."));
    }

    /**
     * Deletes a group based on the provided group ID, user ID, and organization ID.
     *
     * @param groupId
     * @param userId
     * @param orgId
     */
    @Override
    public void deleteGroup(Long groupId, Long userId, Long orgId) {

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, userId);
        Group group = getGroupById(groupId);
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not exist.");
        }

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
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public RecentChatPagedDto getAllGroups(Long userId, Long orgId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        User user = userService.getUserById(userId);
        Org org = orgService.getOrgById(orgId);

        orgMemberService.validateUserIsOrgMember(user, org);

        List<Object[]> rows = groupRepository.findRecentGroupChatsPaged(userId, orgId, size, offset);

        List<RecentChatDto> chats = rows.stream().map(row -> {

            Long groupId = (Long) row[0];
            String groupName = (String) row[1];
            String visibility = (String) row[2];
            String displayPicture = (String) row[3];
            String sender = (String) row[4];
            String content = (String) row[5];
            Boolean includedMedia = (Boolean) row[6];
            LocalDateTime createdAt = ((Timestamp) row[7]).toLocalDateTime();

            LastMessageDto lastMessage = new LastMessageDto();
            lastMessage.setSender(sender);
            lastMessage.setMessage(content);
            lastMessage.setMedia(includedMedia);
            lastMessage.setCreatedAt(TimeFormatterUtil.formatShort(createdAt));

            RecentChatDto dto = new RecentChatDto();
            dto.setChatId(Long.toString(groupId));
            dto.setName(groupName);
            dto.setImage(displayPicture);
            dto.setVisibility(Visibility.valueOf(visibility));
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
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public ChatPreviewDto getGroupChatPreviewByChatId(Long groupId, Long userId, Long orgId) {

        Group group = getGroupById(groupId);
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not exist");
        }

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);

        if (!groupChatMemberService.isUserMemberOfGroup(groupId, userId)) {
            throw new PermissionDeniedException("You are not a member of this group.");
        }

        ChatPreviewDto chatPreviewDto = new ChatPreviewDto();
        chatPreviewDto.setChatId(Long.toString(group.getId()));
        chatPreviewDto.setActive(group.isActive());
        chatPreviewDto.setDisplayName(group.getName());
        chatPreviewDto.setDisplayPicture(group.getDisplayPicture());
        chatPreviewDto.setParticipantsCount(group.getMembers().size());
        Optional<GroupChatPinned> pinnedGroupChat = groupchatPinnedService.getPinnedGroupByPinnedByAndGroup(orgMember.getUser(), group);
        if (pinnedGroupChat.isPresent()) {
            chatPreviewDto.setPinnedId(pinnedGroupChat.get().getId());
        }
        return chatPreviewDto;
    }
}
