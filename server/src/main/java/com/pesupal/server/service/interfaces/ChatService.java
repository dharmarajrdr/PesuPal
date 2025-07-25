package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.dto.response.MessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public interface ChatService {

    MessageDto save(ChatMessageDto chatMessageDto);

    void broadcastMessage(MessageDto messageDto, SimpMessagingTemplate messagingTemplate);
}
