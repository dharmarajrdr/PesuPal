package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.AddReactionDto;
import com.pesupal.server.dto.response.ReactMessageResponseDto;

public interface DirectMessageReactionService {

    ReactMessageResponseDto reactToMessage(Long messageId, AddReactionDto addReactionDto);
}
