package com.pesupal.server.controller.chat;

import com.pesupal.server.dto.request.chat.direct_message.ChatMessageDto;
import com.pesupal.server.dto.response.chat.MessageDto;
import com.pesupal.server.enums.ChatMode;
import com.pesupal.server.exceptions.WebsocketException;
import com.pesupal.server.factory.ChatServiceFactory;
import com.pesupal.server.model.chat.direct_message.DirectMessage;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.service.interfaces.chat.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketController {

    private final ChatServiceFactory chatServiceFactory;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.direct-message")
    public void sendDirectMessageMessage(@Payload ChatMessageDto<DirectMessage> chatMessage) {

        try {
            ChatService<DirectMessage> chatService = chatServiceFactory.getService(ChatMode.DIRECT_MESSAGE);
            MessageDto messageDto = chatService.save(chatMessage);
            chatService.broadcastMessage(messageDto, messagingTemplate);
        } catch (Exception e) {
            WebsocketException error = new WebsocketException(e.getMessage());
            messagingTemplate.convertAndSend("/queue/errors." + chatMessage.getSenderId(), error);
        }
    }

    @MessageMapping("/chat.group-message")
    public void sendGroupMessage(@Payload ChatMessageDto<GroupChatMessage> chatMessage) {

        try {
            ChatService<GroupChatMessage> chatService = chatServiceFactory.getService(ChatMode.GROUP_MESSAGE);
            MessageDto messageDto = chatService.save(chatMessage);
            chatService.broadcastMessage(messageDto, messagingTemplate);
        } catch (Exception e) {
            WebsocketException error = new WebsocketException(e.getMessage());
            messagingTemplate.convertAndSend("/queue/errors." + chatMessage.getSenderId(), error);
        }
    }
}
