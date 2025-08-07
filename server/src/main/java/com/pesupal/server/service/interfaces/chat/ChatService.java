package com.pesupal.server.service.interfaces.chat;

import com.pesupal.server.dto.request.chat.direct_message.ChatMessageDto;
import com.pesupal.server.dto.response.chat.MessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public interface ChatService {

    MessageDto save(ChatMessageDto chatMessageDto);

    void broadcastMessage(MessageDto messageDto, SimpMessagingTemplate messagingTemplate);
}
