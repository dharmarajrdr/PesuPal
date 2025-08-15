package com.pesupal.server.service.interfaces.chat.group_message;

import com.pesupal.server.dto.request.chat.group_message.GetGroupConversationDto;
import com.pesupal.server.dto.response.chat.MessageDto;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.service.interfaces.chat.ChatService;

import java.util.List;

public interface GroupChatMessageService extends ChatService<GroupChatMessage> {

    GroupChatMessage getGroupChatMessageById(Long messageId);

    void deleteGroupMessage(Long messageId);

    void clearGroupChatMessages(String groupId);

    List<MessageDto> getGroupChatMessages(GetGroupConversationDto getGroupConversationDto);

    void markAllGroupMessagesAsRead(String groupId);

    void addSystemMessage(Group group, String message);
}
