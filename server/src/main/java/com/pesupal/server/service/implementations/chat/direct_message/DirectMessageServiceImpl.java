package com.pesupal.server.service.implementations.chat.direct_message;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.chat.RescheduleMessageDto;
import com.pesupal.server.dto.request.chat.direct_message.ChatMessageDto;
import com.pesupal.server.dto.request.chat.direct_message.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.chat.*;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.DateTimeUtil;
import com.pesupal.server.helpers.InputValidator;
import com.pesupal.server.model.chat.MessageStatus;
import com.pesupal.server.model.chat.direct_message.DirectMessage;
import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.chat.direct_message.DirectMessageMediaFile;
import com.pesupal.server.model.chat.direct_message.PinnedDirectMessage;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.RecentPrivateChatProjection;
import com.pesupal.server.repository.chat.direct_message.DirectMessageMediaFileRepository;
import com.pesupal.server.repository.chat.direct_message.DirectMessageRepository;
import com.pesupal.server.security.JwtUtil;
import com.pesupal.server.service.interfaces.MediaService;
import com.pesupal.server.service.interfaces.chat.direct_message.*;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Qualifier("directMessageService")
public class DirectMessageServiceImpl extends CurrentValueRetriever implements DirectMessageService {

    private static final String SCHEDULED_MESSAGE_KEY = "scheduled_direct_messages";

    private final JwtUtil jwtUtil;
    private final MediaService mediaService;
    private final OrgMemberService orgMemberService;
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final DirectMessageRepository directMessageRepository;
    private final DirectMessageChatService directMessageChatService;
    private final PinnedDirectMessageService pinnedDirectMessageService;
    private final DirectMessageReactionService directMessageReactionService;
    private final DirectMessageMediaFileService directMessageMediaFileService;
    private final DirectMessageMediaFileRepository directMessageMediaFileRepository;

    public DirectMessageServiceImpl(DirectMessageRepository directMessageRepository, @Lazy DirectMessageReactionService directMessageReactionService, OrgMemberService orgMemberService, PinnedDirectMessageService pinnedDirectMessageService, DirectMessageMediaFileRepository directMessageMediaFileRepository, DirectMessageMediaFileService directMessageMediaFileService, JwtUtil jwtUtil, DirectMessageChatService directMessageChatService, RedisTemplate<String, Object> redisTemplate, SimpMessagingTemplate messagingTemplate, MediaService mediaService) {
        this.jwtUtil = jwtUtil;
        this.mediaService = mediaService;
        this.redisTemplate = redisTemplate;
        this.orgMemberService = orgMemberService;
        this.messagingTemplate = messagingTemplate;
        this.directMessageRepository = directMessageRepository;
        this.directMessageChatService = directMessageChatService;
        this.pinnedDirectMessageService = pinnedDirectMessageService;
        this.directMessageReactionService = directMessageReactionService;
        this.directMessageMediaFileService = directMessageMediaFileService;
        this.directMessageMediaFileRepository = directMessageMediaFileRepository;
    }

    /**
     * Converts a DirectMessage entity to a MessageDto.
     *
     * @param dm
     * @param orgId
     * @param memo
     * @return
     */
    private MessageDto toMessageDto(DirectMessage dm, Long orgId, Map<Long, UserPreviewDto> memo) {

        MessageDto messageDto = MessageDto.fromDirectMessage(dm);
        Long senderId = dm.getSender().getId();
        Long receiverId = dm.getReceiver().getId();
        if (!memo.containsKey(senderId)) {
            memo.put(senderId, orgMemberService.getUserPreview(dm.getSender()));
        }
        if (!memo.containsKey(receiverId)) {
            memo.put(receiverId, orgMemberService.getUserPreview(dm.getReceiver()));
        }
        messageDto.setSender(memo.get(senderId));
        messageDto.setReceiver(memo.get(receiverId));
        if (dm.getContainsMedia()) {
            Optional<DirectMessageMediaFile> optionalDirectMessageMediaFile = directMessageMediaFileRepository.findByDirectMessage(dm);
            if (optionalDirectMessageMediaFile.isPresent()) {
                DirectMessageMediaFile directMessageMediaFile = optionalDirectMessageMediaFile.get();
                MediaFileDto directMessageMediaFileDto = MediaFileDto.fromDirectMessageMediaFile(directMessageMediaFile);
                directMessageMediaFileDto.setMediaUrl(mediaService.generatePresignedUrl(directMessageMediaFile.getMediaId()));
                messageDto.setMedia(directMessageMediaFileDto);
            }
        }
        messageDto.setReactions(directMessageReactionService.getReactionsCountForMessage(dm));
        return messageDto;
    }

    /**
     * Check whether the user is part of this org
     *
     * @param directMessageChat
     * @param userId
     * @return
     */
    @Override
    public boolean isUserPartOfThisChat(DirectMessageChat directMessageChat, Long userId) {

        return (directMessageChat.getUser1().getId().equals(userId) || directMessageChat.getUser2().getId().equals(userId));
    }

    /**
     * Retrieves direct messages between two users by their IDs.
     *
     * @param getConversationBetweenUsers
     * @return List of MessageDto
     */
    @Override
    public List<MessageDto> getDirectMessagesBetweenUsers(GetConversationBetweenUsers getConversationBetweenUsers) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(getConversationBetweenUsers.getChatId());
        if (!isUserPartOfThisChat(directMessageChat, orgMember.getId())) {
            throw new PermissionDeniedException("You don't have permission to read this chat");
        }

        Pageable pageable = PageRequest.of(getConversationBetweenUsers.getPage(), getConversationBetweenUsers.getSize(), Sort.by("createdAt").descending());
        Page<DirectMessage> messages;
        Long pivotMessageId = getConversationBetweenUsers.getPivotMessageId();
        List<MessageStatus> fetchMessagesWithStatus = List.of(MessageStatus.SENT, MessageStatus.DELETED);
        if (pivotMessageId != null) {
            messages = directMessageRepository.findAllByDirectMessageChatPublicIdAndIdLessThanAndMessageStatusIn(getConversationBetweenUsers.getChatId(), getConversationBetweenUsers.getPivotMessageId(), fetchMessagesWithStatus, pageable);
        } else {
            messages = directMessageRepository.findAllByDirectMessageChatPublicIdAndMessageStatusIn(getConversationBetweenUsers.getChatId(), fetchMessagesWithStatus, pageable);
        }
        Map<Long, UserPreviewDto> memo = new HashMap<>();
        return messages.stream().map(dm -> toMessageDto(dm, orgId, memo)).sorted(Comparator.comparing(MessageDto::getCreatedAt)).toList();
    }

    /**
     * Marks all messages in a chat as read for a specific user.
     *
     * @param chatId
     */
    @Override
    public void markAllMessagesAsRead(String chatId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getId();
        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(chatId);
        if (!isUserPartOfThisChat(directMessageChat, userId)) {
            throw new PermissionDeniedException("You don't have permission to read this chat");
        }

        directMessageRepository.markMessagesAsRead(chatId, userId, ReadReceipt.READ);
    }

    /**
     * Retrieves a specific direct message by its ID.
     *
     * @param messageId
     * @return
     */
    @Override
    public DirectMessage getDirectMessageById(Long messageId) {

        return directMessageRepository.findById(messageId).orElseThrow(() -> new DataNotFoundException("Message with ID " + messageId + " not found"));
    }

    /**
     * Deletes a specific message in a chat by its ID.
     *
     * @param messageId
     */
    @Override
    public void deleteMessage(Long messageId) {

        DirectMessage directMessage = getDirectMessageById(messageId);

        if (directMessage.getSender().getPublicId().equals(getCurrentUserPublicId())) {
            throw new PermissionDeniedException("You do not have permission to delete this message.");
        }

        if (directMessage.getMessageStatus().equals(MessageStatus.DELETED)) {
            throw new ActionProhibitedException("This message has already been deleted.");
        }

        directMessage.setMessageStatus(MessageStatus.DELETED);
        directMessageRepository.save(directMessage);

        directMessageMediaFileService.unlinkMediaFilesByDirectMessage(directMessage);
    }

    /**
     * Retrieves recent chats for a user in a specific organization.
     *
     * @param search
     * @param pageable
     * @return
     */
    @Override
    public RecentChatPagedDto getRecentChatsPaged(String search, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getId();
        Long orgId = orgMember.getOrg().getId();

        List<RecentPrivateChatProjection> rows = directMessageRepository.findRecentChatsPaged(userId, orgId, search, size, offset);

        List<RecentChatDto> chats = rows.stream().map(projection -> {
            LastMessageDto lastMessage = new LastMessageDto();
            lastMessage.setSender(projection.getSenderName());
            if (!projection.getMessageStatus().equals(MessageStatus.DELETED)) {
                lastMessage.setMessage(projection.getContent());
            } else {
                lastMessage.setMessage("This message has been deleted.");
            }
            lastMessage.setMedia(projection.getIncludedMedia());
            lastMessage.setCreatedAt(DateTimeUtil.formatShort(projection.getCreatedAt()));
            lastMessage.setReadReceipt(ReadReceipt.valueOf(projection.getReadReceipt()));
            lastMessage.setMessageStatus(projection.getMessageStatus());
            lastMessage.setMessageType(projection.getMessageType());

            RecentChatDto dto = new RecentChatDto();
            dto.setChatId(projection.getChatPublicId());
            dto.setName(projection.getDisplayName());
            dto.setImage(mediaService.generatePresignedUrl(projection.getDisplayPicture()));
            dto.setStatus(projection.getUserStatus());
            dto.setRecentMessage(lastMessage);

            return dto;
        }).toList();

        Long total = directMessageRepository.countRecentChats(userId);

        return new RecentChatPagedDto(chats, pageable, total);
    }

    /**
     * Saves a chat message to the database.
     *
     * @param chatMessageDto
     */
    @Override
    @Transactional
    public MessageDto save(ChatMessageDto<DirectMessage> chatMessageDto) {

        String token = (String) InputValidator.notNull(chatMessageDto.getToken(), "token");

        Claims claims = jwtUtil.extractAllClaims(token);
        String senderOrgMemberId = claims.get("orgMemberId").toString();

        OrgMember orgMember = orgMemberService.getOrgMemberByPublicId(senderOrgMemberId);
        Org org = orgMember.getOrg();

        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(chatMessageDto.getChatId());

        OrgMember sender = orgMemberService.getOrgMemberByPublicId(senderOrgMemberId);
        OrgMember receiver = directMessageChat.getAnotherUser(sender);

        chatMessageDto.setSenderId(sender.getPublicId());

        boolean containsMedia = chatMessageDto.getMedia() != null;

        DirectMessage directMessage = new DirectMessage();
        directMessage.setSender(sender);
        directMessage.setReceiver(receiver);
        directMessage.setOrg(org);
        directMessage.setDirectMessageChat(directMessageChat);
        directMessage.setContainsMedia(containsMedia);
        directMessage.setReadReceipt(ReadReceipt.SENT);
        directMessage.setMessage(chatMessageDto.getMessage());
        directMessage.setMessageStatus(MessageStatus.SENT);
        if (chatMessageDto.getMessageStatus().equals(MessageStatus.SCHEDULED)) {
            int maxScheduleLimit = StaticConfig.MAXIMUM_MESSAGES_SCHEDULABLE_PER_CHAT;
            int numberOfScheduledMessages = directMessageRepository.countDirectMessagesByDirectMessageChat_PublicIdAndSender_PublicIdAndMessageStatus(
                    chatMessageDto.getChatId(),
                    chatMessageDto.getSenderId(),
                    MessageStatus.SCHEDULED
            );
            if (numberOfScheduledMessages >= maxScheduleLimit) {
                throw new ActionProhibitedException("You can only schedule a maximum of " + maxScheduleLimit + " messages per group chat at a time.");
            }
            if (chatMessageDto.getScheduleAt() == null) {
                throw new ActionProhibitedException("Scheduled messages must have a schedule time.");
            }
            if (chatMessageDto.getScheduleAt().isBefore(LocalDateTime.now())) {
                throw new ActionProhibitedException("Messages cannot be scheduled in the past.");
            }
            directMessage.setMessageStatus(MessageStatus.SCHEDULED);
            directMessage.setCreatedAt(chatMessageDto.getScheduleAt());
        }
        directMessage = directMessageRepository.save(directMessage);
        if (containsMedia) { // Store media file if present
            DirectMessageMediaFile directMessageMediaFile = DirectMessageMediaFile.fromMediaUploadDto(chatMessageDto.getMedia());
            directMessageMediaFile.setDirectMessage(directMessage);
            directMessageMediaFileService.save(directMessageMediaFile);
        }
        if (chatMessageDto.getMessageStatus().equals(MessageStatus.SCHEDULED)) {
            long timestampMillis = DateTimeUtil.toEpochMilli(directMessage.getCreatedAt());
            redisTemplate.opsForZSet().add(SCHEDULED_MESSAGE_KEY, directMessage.getId(), timestampMillis);
        }
        return toMessageDto(directMessage, org.getId(), new HashMap<>());
    }

    /**
     * Retrieves a direct message preview by chat ID for a specific user and
     * organization.
     *
     * @param chatId
     * @return
     */
    @Override
    public ChatPreviewDto getDirectMessagePreviewByChatId(String chatId) {

        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(chatId);
        OrgMember currentUser = getCurrentOrgMember();
        OrgMember otherUser = directMessageChat.getAnotherUser(currentUser);

        ChatPreviewDto chatPreviewDto = new ChatPreviewDto();
        chatPreviewDto.setUserId(otherUser.getPublicId());
        chatPreviewDto.setChatId(chatId);
        chatPreviewDto.setActive(!otherUser.isArchived());
        chatPreviewDto.setDisplayName(otherUser.getDisplayName());
        chatPreviewDto.setDisplayPicture(mediaService.generatePresignedUrl(otherUser.getDisplayPicture()));
        Optional<PinnedDirectMessage> pinnedDirectMessage = pinnedDirectMessageService.getPinnedDirectMessageByPinnedByAndDirectMessageChat(currentUser, directMessageChat);
        pinnedDirectMessage.ifPresent(directMessage -> chatPreviewDto.setPinnedId(directMessage.getId()));
        return chatPreviewDto;
    }

    /**
     * Broadcasts a message to the receiver and sender's topic.
     *
     * @param messageDto
     * @param messagingTemplate
     */
    @Override
    @Async
    public void broadcastMessage(MessageDto messageDto, SimpMessagingTemplate messagingTemplate) {

        messagingTemplate.convertAndSend("/topic/direct-message." + messageDto.getReceiver().getId(), messageDto);

        messagingTemplate.convertAndSend("/topic/message-delivery." + messageDto.getSender().getId(), messageDto);
    }

    /**
     * Schedules a chat message for future delivery.
     *
     * @param chatMessageDto
     */
    @Override
    public void schedule(ChatMessageDto<DirectMessage> chatMessageDto) {

        chatMessageDto.setMessageStatus(MessageStatus.SCHEDULED);
        save(chatMessageDto);
    }

    /**
     * Retrieves scheduled messages for a specific chat.
     *
     * @param chatId
     * @return
     */
    @Override
    public List<MessageDto> getScheduledMessages(String chatId) {

        OrgMember orgMember = getCurrentOrgMember();
        Long orgId = orgMember.getOrg().getId();
        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(chatId);
        if (!isUserPartOfThisChat(directMessageChat, orgMember.getId())) {
            throw new PermissionDeniedException("You don't have permission to read this chat");
        }

        Map<Long, UserPreviewDto> memo = new HashMap<>();
        return directMessageRepository.findAllBySenderAndDirectMessageChatAndMessageStatusAndCreatedAtIsAfterOrderByCreatedAt(orgMember, directMessageChat, MessageStatus.SCHEDULED, LocalDateTime.now()).stream().map(directMessage -> toMessageDto(directMessage, orgId, memo)).toList();
    }

    /**
     * Reschedules a message to be sent at a later time.
     *
     * @param messageId
     * @param rescheduleMessageDto
     */
    @Override
    public void reschedule(Long messageId, RescheduleMessageDto rescheduleMessageDto) {

        OrgMember orgMember = getCurrentOrgMember();
        DirectMessage directMessage = getDirectMessageById(messageId);

        if (!directMessage.getSender().getPublicId().equals(orgMember.getPublicId())) {
            throw new PermissionDeniedException("You do not have permission to reschedule this message.");
        }

        if (directMessage.getMessageStatus() != MessageStatus.SCHEDULED) {
            throw new ActionProhibitedException("This message is not scheduled for rescheduling.");
        }

        if (rescheduleMessageDto.getScheduleAt() == null) {
            throw new ActionProhibitedException("Scheduled messages must have a schedule time.");
        }

        if (rescheduleMessageDto.getScheduleAt().isBefore(LocalDateTime.now())) {
            throw new ActionProhibitedException("Messages cannot be scheduled in the past.");
        }

        directMessage.setCreatedAt(rescheduleMessageDto.getScheduleAt());
        directMessage.setMessageStatus(MessageStatus.SCHEDULED);
        directMessageRepository.save(directMessage);
    }

    /**
     * Unschedules a message by its ID.
     *
     * @param messageId
     */
    @Override
    public void unschedule(Long messageId, Map<Long, UserPreviewDto> memo, OrgMember triggeredBy) {

        Optional<DirectMessage> optionalDirectMessage = directMessageRepository.findById(messageId);
        if (optionalDirectMessage.isEmpty()) {
            return;   // message not found
        }
        DirectMessage directMessage = optionalDirectMessage.get();
        if (!directMessage.getMessageStatus().equals(MessageStatus.SCHEDULED)) {
            return;   // Skip if the message is not scheduled
        }
        if (triggeredBy != null && !directMessage.getSender().getPublicId().equals(triggeredBy.getPublicId())) {
            throw new PermissionDeniedException("You do not have permission to unschedule this message.");
        }
        directMessage.setMessageStatus(MessageStatus.SENT);
        directMessage.setCreatedAt(LocalDateTime.now());
        directMessageRepository.save(directMessage);
        MessageDto messageDto = toMessageDto(directMessage, directMessage.getOrg().getId(), memo);
        broadcastMessage(messageDto, messagingTemplate);
    }

    /**
     * Unschedules all messages in a specific chat.
     *
     * @param chatId
     * @param memo
     */
    @Override
    public void unscheduleAllMessagesInChat(String chatId, Map<Long, UserPreviewDto> memo) {

        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(chatId);
        OrgMember orgMember = getCurrentOrgMember();
        if (!isUserPartOfThisChat(directMessageChat, orgMember.getId())) {
            throw new PermissionDeniedException("You are not part of this chat.");
        }

        List<DirectMessage> scheduledMessages = directMessageRepository.findAllBySenderAndDirectMessageChatAndMessageStatus(orgMember, directMessageChat, MessageStatus.SCHEDULED);
        for (DirectMessage message : scheduledMessages) {
            unschedule(message.getId(), memo, orgMember);
        }
    }

    /**
     * Deletes a scheduled message by its ID.
     *
     * @param messageId
     */
    @Override
    public void deleteSchedule(Long messageId) {

        OrgMember orgMember = getCurrentOrgMember();
        DirectMessage directMessage = getDirectMessageById(messageId);

        if (!directMessage.getSender().getPublicId().equals(orgMember.getPublicId())) {
            throw new PermissionDeniedException("You do not have permission to delete this scheduled message.");
        }

        if (directMessage.getMessageStatus() != MessageStatus.SCHEDULED) {
            throw new ActionProhibitedException("This message is not yet scheduled for deletion.");
        }

        directMessageMediaFileService.unlinkMediaFilesByDirectMessage(directMessage);
        directMessageRepository.delete(directMessage);

        redisTemplate.opsForZSet().remove(SCHEDULED_MESSAGE_KEY, messageId);
    }

    /**
     * Deletes all scheduled messages for a specific chat.
     *
     * @param chatId
     */
    @Override
    public void deleteAllScheduledMessages(String chatId) {

        OrgMember orgMember = getCurrentOrgMember();
        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(chatId);
        if (!isUserPartOfThisChat(directMessageChat, orgMember.getId())) {
            throw new PermissionDeniedException("You don't have permission to read this chat");
        }

        List<DirectMessage> scheduledMessages = directMessageRepository.findAllBySenderAndDirectMessageChatAndMessageStatus(orgMember, directMessageChat, MessageStatus.SCHEDULED);
        for (DirectMessage message : scheduledMessages) {
            directMessageMediaFileService.unlinkMediaFilesByDirectMessage(message);
            directMessageRepository.delete(message);
            redisTemplate.opsForZSet().remove(SCHEDULED_MESSAGE_KEY, message.getId());
        }
    }

    /**
     * Scheduled task to broadcast messages that are due for delivery.
     */
    @Scheduled(cron = "0 * * * * *")
    @Override
    public void broadcastScheduledMessages() {

        long currentTimeMillis = DateTimeUtil.toEpochMilli(LocalDateTime.now());
        Set<Object> messageIds = redisTemplate.opsForZSet().rangeByScore(SCHEDULED_MESSAGE_KEY, 0, currentTimeMillis);
        Map<Long, UserPreviewDto> memo = new HashMap<>();

        for (Object messageId : Objects.requireNonNull(messageIds)) {
            try {
                redisTemplate.opsForZSet().remove(SCHEDULED_MESSAGE_KEY, messageId);
                Long id = Long.parseLong(messageId.toString());
                unschedule(id, memo, null);
            } catch (Exception ignored) {
            }
        }
    }
}
