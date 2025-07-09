package com.pesupal.server.service.implementations;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.DirectMessageResponseDto;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.Chat;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.DirectMessageRepository;
import com.pesupal.server.security.SecurityUtil;
import com.pesupal.server.service.interfaces.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectMessageServiceImpl implements DirectMessageService {

    private final DirectMessageRepository directMessageRepository;
    private final DirectMessageReactionService directMessageReactionService;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final OrgService orgService;
    private final OrgMemberService orgMemberService;

    public DirectMessageServiceImpl(DirectMessageRepository directMessageRepository, @Lazy DirectMessageReactionService directMessageReactionService, SecurityUtil securityUtil, UserService userService, OrgService orgService, OrgMemberService orgMemberService) {
        this.directMessageRepository = directMessageRepository;
        this.directMessageReactionService = directMessageReactionService;
        this.securityUtil = securityUtil;
        this.userService = userService;
        this.orgService = orgService;
        this.orgMemberService = orgMemberService;
    }

    /**
     * Retrieves direct messages between two users by their IDs.
     *
     * @param getConversationBetweenUsers
     * @return List of DirectMessageResponseDto
     */
    @Override
    public List<DirectMessageResponseDto> getDirectMessagesBetweenUsers(GetConversationBetweenUsers getConversationBetweenUsers) {

        Pageable pageable = PageRequest.of(getConversationBetweenUsers.getPage(), getConversationBetweenUsers.getSize(), Sort.by("createdAt").descending());
        Page<DirectMessage> messages = directMessageRepository.findByChatId(getConversationBetweenUsers.getChatId(), pageable);
        return messages.stream().map(dm -> {
            DirectMessageResponseDto directMessageResponseDto = DirectMessageResponseDto.fromDirectMessage(dm);
            directMessageResponseDto.setReactions(directMessageReactionService.getReactionsCountForMessage(dm));
            return directMessageResponseDto;
        }).toList();
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
     * Saves a chat message to the database.
     *
     * @param chatMessageDto
     */
    @Override
    public void save(ChatMessageDto chatMessageDto) {

        Long orgId = RequestContext.getLong("X-ORG-ID");
        Org org = orgService.getOrgById(orgId);

        Long senderId = securityUtil.getCurrentUserId();
        User sender = userService.getUserById(senderId);
        if (!orgMemberService.existsByUserAndOrg(sender, org)) {
            throw new ActionProhibitedException("You are not a member of this organization.");
        }

        Long receiverId = chatMessageDto.getReceiverId();
        User receiver = userService.getUserById(receiverId);
        if (!orgMemberService.existsByUserAndOrg(receiver, org)) {
            throw new ActionProhibitedException("The receiver is not a member of this organization.");
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
        directMessageRepository.save(directMessage);
    }
}
