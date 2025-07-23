package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.ChatMessageDto;
import com.pesupal.server.dto.response.MessageDto;

public interface ChatService {

    MessageDto save(ChatMessageDto chatMessageDto);
}
