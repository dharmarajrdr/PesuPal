package com.pesupal.server.service.interfaces.chat;

import com.pesupal.server.dto.request.chat.RescheduleMessageDto;
import com.pesupal.server.dto.request.chat.direct_message.ChatMessageDto;
import com.pesupal.server.dto.response.chat.MessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

public interface ChatService<T> {

    MessageDto save(ChatMessageDto<T> chatMessageDto);

    void broadcastMessage(MessageDto messageDto, SimpMessagingTemplate messagingTemplate);

    void schedule(ChatMessageDto<T> chatMessageDto);

    List<MessageDto> getScheduledMessages(String chatId);

    void reschedule(Long messageId, RescheduleMessageDto rescheduleMessageDto);

    void unschedule(Long messageId);

    void deleteSchedule(Long messageId);

    void deleteAllScheduledMessages(String chatId);
}
