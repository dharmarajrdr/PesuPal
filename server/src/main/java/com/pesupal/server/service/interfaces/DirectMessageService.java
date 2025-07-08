package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.DirectMessageResponseDto;
import com.pesupal.server.model.chat.DirectMessage;

import java.util.List;

public interface DirectMessageService {

    List<DirectMessageResponseDto> getDirectMessagesBetweenUsers(GetConversationBetweenUsers getConversationBetweenUsers);

    void markAllMessagesAsRead(String chatId, Long userId);

    DirectMessage getDirectMessageById(Long messageId);

    void deleteMessage(Long userId, Long messageId);
}
