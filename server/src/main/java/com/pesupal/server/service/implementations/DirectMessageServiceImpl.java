package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.*;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.Chat;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.helpers.InputValidator;
import com.pesupal.server.helpers.TimeFormatterUtil;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageChat;
import com.pesupal.server.model.chat.DirectMessageMediaFile;
import com.pesupal.server.model.chat.PinnedDirectMessage;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.DirectMessageMediaFileRepository;
import com.pesupal.server.repository.DirectMessageRepository;
import com.pesupal.server.security.JwtUtil;
import com.pesupal.server.service.interfaces.*;
import com.pesupal.server.strategies.media_storage.S3Service;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Qualifier("directMessageService")
public class DirectMessageServiceImpl extends CurrentValueRetriever implements DirectMessageService {

    private final JwtUtil jwtUtil;
    private final S3Service s3Service;
    private final OrgService orgService;
    private final UserService userService;
    private final OrgMemberService orgMemberService;
    private final DirectMessageRepository directMessageRepository;
    private final DirectMessageChatService directMessageChatService;
    private final PinnedDirectMessageService pinnedDirectMessageService;
    private final DirectMessageReactionService directMessageReactionService;
    private final DirectMessageMediaFileService directMessageMediaFileService;
    private final DirectMessageMediaFileRepository directMessageMediaFileRepository;

    public DirectMessageServiceImpl(DirectMessageRepository directMessageRepository, @Lazy DirectMessageReactionService directMessageReactionService, UserService userService, OrgService orgService, OrgMemberService orgMemberService, PinnedDirectMessageService pinnedDirectMessageService, DirectMessageMediaFileRepository directMessageMediaFileRepository, S3Service s3Service, DirectMessageMediaFileService directMessageMediaFileService, JwtUtil jwtUtil, DirectMessageChatService directMessageChatService) {
        this.jwtUtil = jwtUtil;
        this.s3Service = s3Service;
        this.orgService = orgService;
        this.userService = userService;
        this.orgMemberService = orgMemberService;
        this.directMessageRepository = directMessageRepository;
        this.pinnedDirectMessageService = pinnedDirectMessageService;
        this.directMessageReactionService = directMessageReactionService;
        this.directMessageMediaFileService = directMessageMediaFileService;
        this.directMessageMediaFileRepository = directMessageMediaFileRepository;
        this.directMessageChatService = directMessageChatService;
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
        if (!memo.containsKey(senderId)) {
            memo.put(senderId, UserPreviewDto.fromOrgMember(orgMemberService.getOrgMemberByUserIdAndOrgId(senderId, orgId)));
        }
        messageDto.setSender(memo.get(senderId));
        if (dm.getContainsMedia()) {
            DirectMessageMediaFile directMessageMediaFile = directMessageMediaFileRepository.findByDirectMessage(dm);
            if (directMessageMediaFile != null) {
                MediaFileDto directMessageMediaFileDto = MediaFileDto.fromDirectMessageMediaFile(directMessageMediaFile);
                String key = directMessageMediaFile.getMediaId() + "." + directMessageMediaFile.getExtension();
                directMessageMediaFileDto.setMediaUrl(s3Service.generatePresignedUrl(key));
                messageDto.setMedia(directMessageMediaFileDto);
            }
        }
        messageDto.setReactions(directMessageReactionService.getReactionsCountForMessage(dm));
        return messageDto;
    }

    /**
     * Retrieves direct messages between two users by their IDs.
     *
     * @param getConversationBetweenUsers
     * @return List of MessageDto
     */
    @Override
    public List<MessageDto> getDirectMessagesBetweenUsers(GetConversationBetweenUsers getConversationBetweenUsers, Long userId, Long orgId) {

        if (!Chat.isUserInChat(getConversationBetweenUsers.getChatId(), userId)) {
            throw new PermissionDeniedException("You do not have permission to access this chat.");
        }

        Pageable pageable = PageRequest.of(getConversationBetweenUsers.getPage(), getConversationBetweenUsers.getSize(), Sort.by("createdAt").descending());
        Page<DirectMessage> messages = null;
        Long pivotMessageId = getConversationBetweenUsers.getPivotMessageId();
        if (pivotMessageId != null) {
            messages = directMessageRepository.findAllByDirectMessageChatAndIdLessThan(getConversationBetweenUsers.getChatId(), getConversationBetweenUsers.getPivotMessageId(), pageable);
        } else {
            messages = directMessageRepository.findAllByDirectMessageChat(getConversationBetweenUsers.getChatId(), pageable);
        }
        Map<Long, UserPreviewDto> memo = new HashMap<>();
        return messages.stream().map(dm -> toMessageDto(dm, orgId, memo)).sorted(Comparator.comparing(MessageDto::getCreatedAt)).toList();
    }

    /**
     * Marks all messages in a chat as read for a specific user.
     *
     * @param chatId
     * @param userId
     */
    @Override
    public void markAllMessagesAsRead(String chatId, Long userId) {

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

        if (directMessage.isDeleted()) {
            throw new ActionProhibitedException("This message has already been deleted.");
        }

        directMessageRepository.delete(directMessage);
    }

    /**
     * Retrieves recent chats for a user in a specific organization.
     *
     * @param orgMember
     * @param pageable
     * @return
     */
    @Override
    public RecentChatPagedDto getRecentChatsPaged(OrgMember orgMember, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

        Long userId = orgMember.getId();
        Long orgId = orgMember.getOrg().getId();

        User user = userService.getUserById(userId);
        Org org = orgService.getOrgById(orgId);

        orgMemberService.validateUserIsOrgMember(user, org);

        List<Object[]> rows = directMessageRepository.findRecentChatsPaged(userId, orgId, size, offset);

        List<RecentChatDto> chats = rows.stream().map(row -> {
            String displayPicture = (String) row[0];
            String userName = (String) row[1];
            String userStatus = (String) row[2];
            String sender = String.valueOf(row[3]);
            String content = (String) row[4];
            Boolean includedMedia = (Boolean) row[5];
            LocalDateTime createdAt = ((Timestamp) row[6]).toLocalDateTime();
            ReadReceipt readReceipt = ReadReceipt.valueOf((String) row[7]);
            String chatId = (String) row[8];

            LastMessageDto lastMessage = new LastMessageDto();
            lastMessage.setSender(sender);
            lastMessage.setMessage(content);
            lastMessage.setMedia(includedMedia);
            lastMessage.setCreatedAt(TimeFormatterUtil.formatShort(createdAt));
            lastMessage.setReadReceipt(readReceipt);

            RecentChatDto dto = new RecentChatDto();
            dto.setChatId(chatId);
            dto.setName(userName);
            dto.setImage(displayPicture);
            dto.setStatus(userStatus);
            dto.setRecentMessage(lastMessage);

            return dto;
        }).toList();

        Long total = directMessageRepository.countRecentChats(userId, orgId);

        return new RecentChatPagedDto(chats, pageable, total);
    }

    /**
     * Saves a chat message to the database.
     *
     * @param chatMessageDto
     */
    @Override
    @Transactional
    public MessageDto save(ChatMessageDto chatMessageDto) {

        Long orgId = chatMessageDto.getOrgId();
        Org org = orgService.getOrgById(orgId);

        String token = (String) InputValidator.notNull(chatMessageDto.getToken(), "token");

        Claims claims = jwtUtil.extractAllClaims(token);
        String senderOrgMemberId = claims.get("orgMemberId").toString();

        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(chatMessageDto.getChatId());

        OrgMember sender = orgMemberService.getOrgMemberByPublicId(senderOrgMemberId);
        OrgMember receiver = directMessageChat.getAnotherUser(sender);

        boolean containsMedia = chatMessageDto.getMedia() != null;

        DirectMessage directMessage = new DirectMessage();
        directMessage.setSender(sender);
        directMessage.setReceiver(receiver);
        directMessage.setOrg(org);
        directMessage.setDirectMessageChat(directMessageChat);
        directMessage.setDeleted(false);
        directMessage.setContainsMedia(containsMedia);
        directMessage.setReadReceipt(ReadReceipt.SENT);
        directMessage.setMessage(chatMessageDto.getMessage());
        directMessage = directMessageRepository.save(directMessage);
        if (containsMedia) { // Store media file if present
            DirectMessageMediaFile directMessageMediaFile = DirectMessageMediaFile.fromMediaUploadDto(chatMessageDto.getMedia());
            directMessageMediaFile.setDirectMessage(directMessage);
            directMessageMediaFileService.save(directMessageMediaFile);
        }
        return toMessageDto(directMessage, orgId, new HashMap<>());
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
        chatPreviewDto.setDisplayPicture(otherUser.getDisplayPicture());
        Optional<PinnedDirectMessage> pinnedDirectMessage = pinnedDirectMessageService.getPinnedDirectMessageByPinnedByAndDirectMessageChat(currentUser, directMessageChat);
        if (pinnedDirectMessage.isPresent()) {
            chatPreviewDto.setPinnedId(pinnedDirectMessage.get().getId());
        }
        return chatPreviewDto;
    }

    /**
     * Broadcasts a message to the receiver and sender's topic.
     *
     * @param messageDto
     * @param messagingTemplate
     */
    @Override
    public void broadcastMessage(MessageDto messageDto, SimpMessagingTemplate messagingTemplate) {

        messagingTemplate.convertAndSend("/topic/direct-message." + messageDto.getReceiverId(), messageDto);
        messagingTemplate.convertAndSend("/topic/message-delivery." + messageDto.getSender().getId(), messageDto);
    }
}
