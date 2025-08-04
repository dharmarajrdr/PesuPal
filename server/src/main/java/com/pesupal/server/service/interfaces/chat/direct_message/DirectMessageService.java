package com.pesupal.server.service.interfaces.chat.direct_message;

import com.pesupal.server.dto.request.chat.direct_message.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.dto.response.chat.ChatPreviewDto;
import com.pesupal.server.dto.response.chat.RecentChatPagedDto;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageChat;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.service.interfaces.chat.ChatService;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DirectMessageService extends ChatService {

    List<MessageDto> getDirectMessagesBetweenUsers(GetConversationBetweenUsers getConversationBetweenUsers);

    boolean isUserPartOfThisChat(DirectMessageChat directMessageChat, Long userId);

    void markAllMessagesAsRead(String chatId);

    DirectMessage getDirectMessageById(Long messageId);

    void deleteMessage(Long messageId);

    RecentChatPagedDto getRecentChatsPaged(OrgMember orgMember, Pageable pageable);

    ChatPreviewDto getDirectMessagePreviewByChatId(String chatId);
}
