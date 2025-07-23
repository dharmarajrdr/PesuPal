package com.pesupal.server.controller;

import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.exceptions.WebsocketException;
import com.pesupal.server.service.interfaces.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.direct-message")
    public void sendMessage(@Payload ChatMessageDto chatMessage) {

        try {
            chatService.save(chatMessage);
            messagingTemplate.convertAndSend("/topic/user." + chatMessage.getReceiverId(), chatMessage);
            messagingTemplate.convertAndSend("/topic/message-delivery." + chatMessage.getSenderId(), chatMessage);
        } catch (Exception e) {
            WebsocketException error = new WebsocketException(e.getMessage());
            messagingTemplate.convertAndSend("/queue/errors." + chatMessage.getSenderId(), error);
        }
    }
}
