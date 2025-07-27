package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.dto.request.GetGroupConversationDto;
import com.pesupal.server.dto.response.MediaFileDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.InputValidator;
import com.pesupal.server.model.group.*;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.repository.GroupChatMessageRepository;
import com.pesupal.server.repository.GroupMessageMediaFileRepository;
import com.pesupal.server.security.JwtUtil;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.group.*;
import com.pesupal.server.strategies.media_storage.S3Service;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Qualifier("groupChatMessageService")
public class GroupChatMessageServiceImpl extends CurrentValueRetriever implements GroupChatMessageService {

    private final JwtUtil jwtUtil;
    private final S3Service s3Service;
    private final GroupService groupService;
    private final OrgMemberService orgMemberService;
    private final GroupChatMemberService groupChatMemberService;
    private final GroupChatReactionService groupChatReactionService;
    private final GroupChatMemberRepository groupChatMemberRepository;
    private final GroupChatMessageRepository groupChatMessageRepository;
    private final GroupChatConfigurationService groupChatConfigurationService;
    private final GroupMessageMediaFileRepository groupMessageMediaFileRepository;

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
     * @return
     */
    @Override
    public List<MessageDto> getGroupChatMessages(GetGroupConversationDto getGroupConversationDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();

        Pageable pageable = PageRequest.of(
                getGroupConversationDto.getPage(),
                getGroupConversationDto.getSize(),
                Sort.by("createdAt").descending());
        Page<GroupChatMessage> messages = null;
        Long pivotMessageId = getGroupConversationDto.getPivotMessageId();
        if (pivotMessageId != null) {
            messages = groupChatMessageRepository.findAllByGroup_PublicIdAndIdLessThan(getGroupConversationDto.getGroupId(), getGroupConversationDto.getPivotMessageId(), pageable);
        } else {
            messages = groupChatMessageRepository.findAllByGroup_PublicId(getGroupConversationDto.getGroupId(), pageable);
        }
        Map<Long, UserPreviewDto> memo = new HashMap<>();
        return messages.stream().map(gm -> {
            MessageDto messageDto = MessageDto.fromGroupMessage(gm);
            Long senderId = gm.getSender().getId();
            if (!memo.containsKey(senderId)) {
                memo.put(senderId, UserPreviewDto.fromOrgMember(orgMemberService.getOrgMemberByUserIdAndOrgId(senderId, orgId)));
            }
            messageDto.setSender(memo.get(senderId));
            if (gm.isContainsMedia()) {
                GroupMessageMediaFile groupMessageMediaFile = groupMessageMediaFileRepository.findByGroupChatMessage(gm);
                if (groupMessageMediaFile != null) {
                    MediaFileDto mediaFileDto = MediaFileDto.fromGroupMessageMediaFile(groupMessageMediaFile);
                    String key = groupMessageMediaFile.getMediaId() + "." + groupMessageMediaFile.getExtension();
                    mediaFileDto.setMediaUrl(s3Service.generatePresignedUrl(key));
                    messageDto.setMedia(mediaFileDto);
                }
            }
            messageDto.setReactions(groupChatReactionService.getReactionsCountForMessage(gm));
            return messageDto;
        }).sorted(Comparator.comparing(MessageDto::getCreatedAt)).toList();
    }

    /**
     * Marks all messages in a group as read.
     *
     * @param groupId
     * @param userId
     * @param orgId
     */
    @Override
    public void markAllGroupMessagesAsRead(Long groupId, Long userId, Long orgId) {

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, userId);
        if (!groupChatMember.isActive()) {
            throw new PermissionDeniedException("You're not part of this group anymore.");
        }

        Group group = groupChatMember.getGroup();
        Optional<GroupChatMessage> lastMessageOfGroup = groupChatMessageRepository.findFirstByGroupOrderByCreatedAtDesc(group);
        if (lastMessageOfGroup.isPresent()) {
            groupChatMember.setLastReadMessage(lastMessageOfGroup.get());
        }

        groupChatMemberRepository.save(groupChatMember);
    }

    /**
     * Converts a DirectMessage entity to a MessageDto.
     *
     * @param gm
     * @param orgId
     * @param memo
     * @return
     */
    private MessageDto toMessageDto(GroupChatMessage gm, Long orgId, Map<Long, UserPreviewDto> memo) {

        MessageDto messageDto = MessageDto.fromGroupMessage(gm);
        Long senderId = gm.getSender().getId();
        if (!memo.containsKey(senderId)) {
            memo.put(senderId, UserPreviewDto.fromOrgMember(orgMemberService.getOrgMemberByUserIdAndOrgId(senderId, orgId)));
        }
        messageDto.setSender(memo.get(senderId));
        if (gm.isContainsMedia()) {
            GroupMessageMediaFile groupMessageMediaFile = groupMessageMediaFileRepository.findByGroupChatMessage(gm);
            if (groupMessageMediaFile != null) {
                MediaFileDto groupMessageMediaFileDto = MediaFileDto.fromGroupMessageMediaFile(groupMessageMediaFile);
                String key = groupMessageMediaFile.getMediaId() + "." + groupMessageMediaFile.getExtension();
                groupMessageMediaFileDto.setMediaUrl(s3Service.generatePresignedUrl(key));
                messageDto.setMedia(groupMessageMediaFileDto);
            }
        }
        messageDto.setReactions(groupChatReactionService.getReactionsCountForMessage(gm));
        return messageDto;
    }

    /**
     * Saves a chat message.
     *
     * @param chatMessageDto
     * @return
     */
    @Override
    public MessageDto save(ChatMessageDto chatMessageDto) {

        String token = (String) InputValidator.notNull(chatMessageDto.getToken(), "token");

        Claims claims = jwtUtil.extractAllClaims(token);
        String senderOrgMemberId = claims.get("orgMemberId").toString();

        OrgMember sender = orgMemberService.getOrgMemberByPublicId(senderOrgMemberId);
        Org org = sender.getOrg();

        chatMessageDto.setSenderId(sender.getPublicId());

        String groupId = chatMessageDto.getChatId();

        GroupChatMember groupChatMember = groupChatMemberService.getGroupMemberByGroupIdAndUserId(groupId, sender.getId());
        if (!groupChatMember.isActive()) {
            throw new PermissionDeniedException("You are no longer part of this group.");
        }

        Group group = groupService.getGroupByPublicId(groupId);
        if (!group.isActive()) {
            throw new PermissionDeniedException("This group is no longer active.");
        }

        boolean containsMedia = chatMessageDto.getMedia() != null;

        GroupChatMessage groupChatMessage = new GroupChatMessage();
        groupChatMessage.setGroup(group);
        groupChatMessage.setDeleted(false);
        groupChatMessage.setSender(sender);
        groupChatMessage.setMessage(chatMessageDto.getMessage());
        groupChatMessage.setContainsMedia(containsMedia);
        groupChatMessage = groupChatMessageRepository.save(groupChatMessage);
        if (containsMedia) { // Store media file if present
            GroupMessageMediaFile groupMessageMediaFile = GroupMessageMediaFile.fromMediaUploadDto(chatMessageDto.getMedia());
            groupMessageMediaFile.setGroupChatMessage(groupChatMessage);
            groupMessageMediaFileRepository.save(groupMessageMediaFile);
        }
        return toMessageDto(groupChatMessage, org.getId(), new HashMap<>());
    }

    /**
     * Broadcasts a message to all subscribers of the topic.
     *
     * @param messageDto
     * @param messagingTemplate
     */
    @Override
    public void broadcastMessage(MessageDto messageDto, SimpMessagingTemplate messagingTemplate) {

    }
}
