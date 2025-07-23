package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.*;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.Chat;
import com.pesupal.server.helpers.TimeFormatterUtil;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageMediaFile;
import com.pesupal.server.model.chat.PinnedDirectMessage;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.DirectMessageMediaFileRepository;
import com.pesupal.server.repository.DirectMessageRepository;
import com.pesupal.server.service.interfaces.*;
import com.pesupal.server.strategies.media_storage.S3Service;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DirectMessageServiceImpl implements DirectMessageService {

    private final S3Service s3Service;
    private final OrgService orgService;
    private final UserService userService;
    private final OrgMemberService orgMemberService;
    private final DirectMessageRepository directMessageRepository;
    private final PinnedDirectMessageService pinnedDirectMessageService;
    private final DirectMessageReactionService directMessageReactionService;
    private final DirectMessageMediaFileRepository directMessageMediaFileRepository;

    public DirectMessageServiceImpl(DirectMessageRepository directMessageRepository, @Lazy DirectMessageReactionService directMessageReactionService, UserService userService, OrgService orgService, OrgMemberService orgMemberService, PinnedDirectMessageService pinnedDirectMessageService, DirectMessageMediaFileRepository directMessageMediaFileRepository, S3Service s3Service) {
        this.directMessageRepository = directMessageRepository;
        this.orgService = orgService;
        this.userService = userService;
        this.orgMemberService = orgMemberService;
        this.pinnedDirectMessageService = pinnedDirectMessageService;
        this.directMessageReactionService = directMessageReactionService;
        this.directMessageMediaFileRepository = directMessageMediaFileRepository;
        this.s3Service = s3Service;
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
            messages = directMessageRepository.findAllByChatIdAndIdLessThan(getConversationBetweenUsers.getChatId(), getConversationBetweenUsers.getPivotMessageId(), pageable);
        } else {
            messages = directMessageRepository.findAllByChatId(getConversationBetweenUsers.getChatId(), pageable);
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
     * @param userId
     * @param messageId
     */
    @Override
    public void deleteMessage(Long userId, Long messageId) {

        DirectMessage directMessage = getDirectMessageById(messageId);

        if (directMessage.getSender().getId() != userId) {
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
     * @param orgId
     * @param userId
     * @return
     */
    @Override
    public RecentChatPagedDto getRecentChatsPaged(Long userId, Long orgId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page * size;

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
    public MessageDto save(ChatMessageDto chatMessageDto) {

        Long orgId = chatMessageDto.getOrgId();
        Org org = orgService.getOrgById(orgId);

        Long senderId = chatMessageDto.getSenderId();
        User sender = userService.getUserById(senderId);
        OrgMember senderOrgMember = orgMemberService.getOrgMemberByUserAndOrg(sender, org);
        if (senderOrgMember.isArchived()) {
            throw new PermissionDeniedException("You are no longer a member of this org.");
        }

        Long receiverId = chatMessageDto.getReceiverId();
        User receiver = userService.getUserById(receiverId);
        OrgMember receiverOrgMember = orgMemberService.getOrgMemberByUserAndOrg(receiver, org);
        if (receiverOrgMember.isArchived()) {
            throw new ActionProhibitedException("User " + receiverOrgMember.getDisplayName() + " is no longer a member of this org.");
        }

        String chatId = Chat.getChatId(senderId, receiverId, orgId);
        DirectMessage directMessage = new DirectMessage();
        directMessage.setSender(sender);
        directMessage.setReceiver(receiver);
        directMessage.setOrg(org);
        directMessage.setChatId(chatId);
        directMessage.setDeleted(false);
        directMessage.setContainsMedia(false);
        directMessage.setReadReceipt(ReadReceipt.SENT);
        directMessage.setMessage(chatMessageDto.getMessage());
        directMessage = directMessageRepository.save(directMessage);
        return toMessageDto(directMessage, orgId, new HashMap<>());
    }

    /**
     * Retrieves a direct message preview by chat ID for a specific user and
     * organization.
     *
     * @param chatId
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public ChatPreviewDto getDirectMessagePreviewByChatId(String chatId, Long userId, Long orgId) {

        if (!Chat.isUserInChat(chatId, userId)) {
            throw new PermissionDeniedException("You do not have permission to access this chat.");
        }

        Long[] parsedChatId = Chat.parseChatId(chatId);
        if (!orgId.equals(parsedChatId[2])) {
            throw new PermissionDeniedException("Chat not found in the specified organization.");
        }

        Long otherUserId = parsedChatId[0].equals(userId) ? parsedChatId[1] : parsedChatId[0];

        OrgMember otherUserOrgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(otherUserId, orgId);

        ChatPreviewDto chatPreviewDto = new ChatPreviewDto();
        chatPreviewDto.setUserId(otherUserId);
        chatPreviewDto.setChatId(chatId);
        chatPreviewDto.setActive(!otherUserOrgMember.isArchived());
        chatPreviewDto.setDisplayName(otherUserOrgMember.getDisplayName());
        chatPreviewDto.setDisplayPicture(otherUserOrgMember.getDisplayPicture());
        Optional<PinnedDirectMessage> pinnedDirectMessage = pinnedDirectMessageService.getPinnedDirectMessageByPinnedByIdAndPinnedUserIdAndOrgId(userId, otherUserId, orgId);
        if (pinnedDirectMessage.isPresent()) {
            chatPreviewDto.setPinnedId(pinnedDirectMessage.get().getId());
        }
        return chatPreviewDto;
    }
}
