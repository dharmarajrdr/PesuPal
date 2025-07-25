package com.pesupal.server.factory;

import com.pesupal.server.enums.ChatMode;
import com.pesupal.server.service.interfaces.ChatService;
import com.pesupal.server.service.interfaces.DirectMessageService;
import com.pesupal.server.service.interfaces.group.GroupChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatServiceFactory {

    private final Map<ChatMode, ChatService> serviceMap;

    @Autowired
    public ChatServiceFactory(
            @Qualifier("directMessageService") DirectMessageService directMessageService,
            @Qualifier("groupChatMessageService") GroupChatMessageService groupChatMessageService
    ) {

        serviceMap = new HashMap<>();
        serviceMap.put(ChatMode.DIRECT_MESSAGE, directMessageService);
        serviceMap.put(ChatMode.GROUP_MESSAGE, groupChatMessageService);
    }

    public ChatService getService(ChatMode type) {

        return serviceMap.get(type);
    }
}
