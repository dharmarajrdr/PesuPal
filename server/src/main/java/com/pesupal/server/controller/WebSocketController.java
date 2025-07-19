package com.pesupal.server.controller;

import com.pesupal.server.dto.request.ChatMessageDto;
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

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessageDto chatMessage) {

        chatService.save(chatMessage);

        messagingTemplate.convertAndSend("/topic/user." + chatMessage.getReceiverId(), chatMessage);
    }
}
