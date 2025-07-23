package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.ChatMessageDto;

public interface ChatService {

    void save(ChatMessageDto chatMessageDto);
}
