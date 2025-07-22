package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.DirectMessagePreviewDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.dto.response.RecentChatPagedDto;
import com.pesupal.server.model.chat.DirectMessage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DirectMessageService extends ChatService {

    List<MessageDto> getDirectMessagesBetweenUsers(GetConversationBetweenUsers getConversationBetweenUsers);

    void markAllMessagesAsRead(String chatId, Long userId);

    DirectMessage getDirectMessageById(Long messageId);

    void deleteMessage(Long userId, Long messageId);

    RecentChatPagedDto getRecentChatsPaged(Long userId, Long orgId, Pageable pageable);

    DirectMessagePreviewDto getDirectMessagePreviewByChatId(String chatId, Long userId, Long orgId);
}
