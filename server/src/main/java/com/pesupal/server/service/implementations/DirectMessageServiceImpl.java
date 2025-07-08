package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.DirectMessageResponseDto;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.repository.DirectMessageRepository;
import com.pesupal.server.service.interfaces.DirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DirectMessageServiceImpl implements DirectMessageService {

    private final DirectMessageRepository directMessageRepository;

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
        return messages.stream().map(DirectMessageResponseDto::fromDirectMessage).toList();
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
     * Deletes a specific message in a chat by its ID.
     *
     * @param userId
     * @param messageId
     */
    @Override
    public void deleteMessage(Long userId, Long messageId) {

        DirectMessage directMessage = directMessageRepository.findById(messageId).orElseThrow(() -> new DataNotFoundException("Message with ID " + messageId + " not found"));

        if (directMessage.getSender().getId() != userId) {
            throw new PermissionDeniedException("You do not have permission to delete this message.");
        }

        if (directMessage.isDeleted()) {
            throw new ActionProhibitedException("This message has already been deleted.");
        }

        directMessageRepository.delete(directMessage);
    }
}
