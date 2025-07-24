package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.GetGroupConversationDto;
import com.pesupal.server.dto.request.group.CreateGroupMessageDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.dto.response.group.GroupMessageDto;
import com.pesupal.server.model.group.GroupChatMessage;
import com.pesupal.server.service.interfaces.ChatService;

import java.util.List;

public interface GroupChatMessageService extends ChatService {

    GroupMessageDto postMessageInGroup(CreateGroupMessageDto createGroupMessageDto, Long userId, Long orgId);

    GroupChatMessage getGroupChatMessageById(Long messageId);

    void deleteGroupMessage(Long messageId, Long userId, Long orgId);

    void clearGroupChatMessages(Long groupId, Long userId, Long orgId);

    List<MessageDto> getGroupChatMessages(GetGroupConversationDto getGroupConversationDto, Long userId, Long orgId);

    void markAllGroupMessagesAsRead(Long groupId, Long currentUserId, Long currentOrgId);
}
