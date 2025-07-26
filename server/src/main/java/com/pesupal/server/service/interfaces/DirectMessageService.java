package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.ChatPreviewDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.dto.response.RecentChatPagedDto;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DirectMessageService extends ChatService {

    List<MessageDto> getDirectMessagesBetweenUsers(GetConversationBetweenUsers getConversationBetweenUsers, Long userId, Long orgId);

    void markAllMessagesAsRead(String chatId, Long userId);

    DirectMessage getDirectMessageById(Long messageId);

    void deleteMessage(Long userId, Long messageId);

    RecentChatPagedDto getRecentChatsPaged(OrgMember orgMember, Pageable pageable);

    ChatPreviewDto getDirectMessagePreviewByChatId(String chatId, Long userId, Long orgId);
}
