package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.ChatMessageDto;

public interface ChatService {

    public void save(ChatMessageDto chatMessageDto);
}
