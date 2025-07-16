package com.pesupal.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.service.interfaces.ChatService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatService chatService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    public ChatWebSocketHandler(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery(); // senderId=1
        Long userId = Long.valueOf(query.split("=")[1]);
        userSessions.put(userId, session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        ChatMessageDto chatMessage = objectMapper.readValue(message.getPayload(), ChatMessageDto.class);

        chatService.save(chatMessage);

        WebSocketSession receiverSession = userSessions.get(chatMessage.getReceiverId());
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        }
    }

}
