package com.pesupal.server.service.interfaces.chat;

import com.pesupal.server.dto.request.chat.RescheduleMessageDto;
import com.pesupal.server.dto.request.chat.direct_message.ChatMessageDto;
import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.dto.response.chat.MessageDto;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Map;

public interface ChatService<T> {

    MessageDto save(ChatMessageDto<T> chatMessageDto);

    void broadcastMessage(MessageDto messageDto, SimpMessagingTemplate messagingTemplate);

    void schedule(ChatMessageDto<T> chatMessageDto);

    List<MessageDto> getScheduledMessages(String chatId);

    void reschedule(Long messageId, RescheduleMessageDto rescheduleMessageDto);

    void unschedule(Long messageId, Map<Long, UserPreviewDto> memo, OrgMember triggeredBy);

    void unscheduleAllMessagesInChat(String chatId, Map<Long, UserPreviewDto> memo);

    void deleteSchedule(Long messageId);

    void deleteAllScheduledMessages(String chatId);

    void unscheduleAllMessagesByOrgMember(OrgMember orgMember);
}
