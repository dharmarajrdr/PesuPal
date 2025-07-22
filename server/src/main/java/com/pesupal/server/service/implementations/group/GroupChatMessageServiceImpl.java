package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.GetGroupConversationDto;
import com.pesupal.server.dto.request.group.CreateGroupMessageDto;
import com.pesupal.server.dto.response.MediaFileDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.dto.response.group.GroupMessageDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.group.*;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.GroupChatMessageRepository;
import com.pesupal.server.repository.GroupMessageMediaFileRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.group.GroupChatConfigurationService;
import com.pesupal.server.service.interfaces.group.GroupChatMemberService;
import com.pesupal.server.service.interfaces.group.GroupChatMessageService;
import com.pesupal.server.service.interfaces.group.GroupChatReactionService;
import com.pesupal.server.strategies.media_storage.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupChatMessageServiceImpl implements GroupChatMessageService {

    private final S3Service s3Service;
    private final OrgMemberService orgMemberService;
    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatReactionService groupChatReactionService;
    private final GroupChatMessageRepository groupChatMessageRepository;
    private final GroupChatConfigurationService groupChatConfigurationService;
    private final GroupMessageMediaFileRepository groupMessageMediaFileRepository;

    /**
     * Posts a message in a group chat.
     *
     * @param createGroupMessageDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public GroupMessageDto postMessageInGroup(CreateGroupMessageDto createGroupMessageDto, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(createGroupMessageDto.getGroupId(), userId);
        Group group = groupChatMember.getGroup();
        if (!group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group not found with ID " + createGroupMessageDto.getGroupId() + ".");
        }

        if (!group.isActive()) {
            throw new ActionProhibitedException("This group is no longer active.");
        }

        Role role = groupChatMember.getRole();
        GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, role);
        if (!groupChatConfiguration.isPostMessage()) {
            throw new PermissionDeniedException("You do not have permission to post messages in this group.");
        }

        GroupChatMessage groupChatMessage = createGroupMessageDto.toGroupChatMessage();
        groupChatMessage.setSender(groupChatMember.getUser());
        groupChatMessage.setDeleted(false);
        groupChatMessage.setGroup(group);
        groupChatMessageRepository.save(groupChatMessage);
        return GroupMessageDto.fromGroupMessageAndOrgMember(groupChatMessage, orgMember);
    }

    /**
     * Retrieves a group chat message by its ID.
     *
     * @param messageId
     * @return
     */
    @Override
    public GroupChatMessage getGroupChatMessageById(Long messageId) {

        return groupChatMessageRepository.findById(messageId).orElseThrow(() -> new DataNotFoundException("Group message not found with ID " + messageId + "."));
    }

    /**
     * Deletes a group message.
     *
     * @param messageId
     * @param userId
     * @param orgId
     */
    @Override
    public void deleteGroupMessage(Long messageId, Long userId, Long orgId) {

        GroupChatMessage groupChatMessage = getGroupChatMessageById(messageId);
        Group group = groupChatMessage.getGroup();
        Org org = group.getOrg();
        if (!org.getId().equals(orgId)) {
            throw new DataNotFoundException("Message not found in this organization.");
        }

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(group.getId(), userId);
        if (!groupChatMember.isActive()) {
            throw new PermissionDeniedException("You're not part of this group anymore.");
        }

        if (!group.isActive()) {
            throw new ActionProhibitedException("This group is no longer active.");
        }

        if (groupChatMessage.isDeleted()) {
            throw new ActionProhibitedException("This message has already been deleted.");
        }

        Role role = groupChatMember.getRole();

        if (!role.equals(Role.SUPER_ADMIN)) {
            GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, role);
            if (!groupChatConfiguration.isDeleteMessage()) {
                throw new PermissionDeniedException("You do not have permission to delete messages in this group.");
            }
        }

        groupChatMessageRepository.delete(groupChatMessage);
    }

    /**
     * Clears all messages in a group chat.
     *
     * @param groupId
     * @param userId
     * @param orgId
     */
    @Override
    public void clearGroupChatMessages(Long groupId, Long userId, Long orgId) {

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, userId);
        Group group = groupChatMember.getGroup();

        if (group.getOrg().getId().equals(orgId)) {
            throw new DataNotFoundException("Group with ID " + groupId + " does not exist.");
        }

        if (!groupChatMember.isActive()) {
            throw new PermissionDeniedException("You're not part of this group anymore.");
        }

        if (!group.isActive()) {
            throw new ActionProhibitedException("This group is no longer active.");
        }

        Role role = groupChatMember.getRole();

        if (!role.equals(Role.SUPER_ADMIN)) {

            GroupChatConfiguration groupChatConfiguration = groupChatConfigurationService.getConfigurationByGroupAndRole(group, role);
            if (!groupChatConfiguration.isClearChat()) {
                throw new PermissionDeniedException("You do not have permission to clear messages in this group.");
            }
        }

        groupChatMessageRepository.deleteAllByGroup(group);
    }

    /**
     * Retrieves messages from a group chat based on the provided criteria.
     *
     * @param getGroupConversationDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<MessageDto> getGroupChatMessages(GetGroupConversationDto getGroupConversationDto, Long userId, Long orgId) {

        Pageable pageable = PageRequest.of(
                getGroupConversationDto.getPage(),
                getGroupConversationDto.getSize(),
                Sort.by("createdAt").descending());
        Page<GroupChatMessage> messages = null;
        Long pivotMessageId = getGroupConversationDto.getPivotMessageId();
        if (pivotMessageId != null) {
            messages = groupChatMessageRepository.findAllByGroupIdAndIdLessThan(getGroupConversationDto.getGroupId(), getGroupConversationDto.getPivotMessageId(), pageable);
        } else {
            messages = groupChatMessageRepository.findAllByGroupId(getGroupConversationDto.getGroupId(), pageable);
        }
        return messages.stream().map(gm -> {
            MessageDto directMessageResponseDto = MessageDto.fromGroupMessage(gm);
            if (gm.isContainsMedia()) {
                GroupMessageMediaFile groupMessageMediaFile = groupMessageMediaFileRepository.findByGroupChatMessage(gm);
                if (groupMessageMediaFile != null) {
                    MediaFileDto mediaFileDto = MediaFileDto.fromGroupMessageMediaFile(groupMessageMediaFile);
                    String key = groupMessageMediaFile.getMediaId() + "." + groupMessageMediaFile.getExtension();
                    mediaFileDto.setMediaUrl(s3Service.generatePresignedUrl(key));
                    directMessageResponseDto.setMedia(mediaFileDto);
                }
            }
            directMessageResponseDto.setReactions(groupChatReactionService.getReactionsCountForMessage(gm));
            return directMessageResponseDto;
        }).sorted(Comparator.comparing(MessageDto::getCreatedAt)).toList();
    }
}
