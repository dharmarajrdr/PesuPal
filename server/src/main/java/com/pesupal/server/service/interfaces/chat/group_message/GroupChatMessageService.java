package com.pesupal.server.service.interfaces.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.GetGroupConversationDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.model.group.GroupChatMessage;
import com.pesupal.server.service.interfaces.chat.ChatService;

import java.util.List;

public interface GroupChatMessageService extends ChatService {

    GroupChatMessage getGroupChatMessageById(Long messageId);

    void deleteGroupMessage(Long messageId, Long userId, Long orgId);

    void clearGroupChatMessages(String groupId);

    List<MessageDto> getGroupChatMessages(GetGroupConversationDto getGroupConversationDto);

    void markAllGroupMessagesAsRead(String groupId);
}
