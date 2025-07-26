package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.GetGroupConversationDto;
import com.pesupal.server.dto.request.group.CreateGroupMessageDto;
import com.pesupal.server.dto.response.MessageDto;
import com.pesupal.server.dto.response.group.GroupMessageDto;
import com.pesupal.server.model.group.GroupChatMessage;
import com.pesupal.server.service.interfaces.ChatService;

import java.util.List;

public interface GroupChatMessageService extends ChatService {

    GroupMessageDto postMessageInGroup(CreateGroupMessageDto createGroupMessageDto);

    GroupChatMessage getGroupChatMessageById(Long messageId);

    void deleteGroupMessage(Long messageId);

    void clearGroupChatMessages(Long groupId);

    List<MessageDto> getGroupChatMessages(GetGroupConversationDto getGroupConversationDto);

    void markAllGroupMessagesAsRead(Long groupId);
}
