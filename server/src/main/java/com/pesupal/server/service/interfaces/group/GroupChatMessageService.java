package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.GetGroupConversationDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.model.group.GroupChatMessage;
import com.pesupal.server.service.interfaces.ChatService;

import java.util.List;

public interface GroupChatMessageService extends ChatService {

    GroupChatMessage getGroupChatMessageById(Long messageId);

    void deleteGroupMessage(Long messageId, Long userId, Long orgId);

    void clearGroupChatMessages(Long groupId, Long userId, Long orgId);

    List<MessageDto> getGroupChatMessages(GetGroupConversationDto getGroupConversationDto);

    void markAllGroupMessagesAsRead(Long groupId, Long currentUserId, Long currentOrgId);
}
