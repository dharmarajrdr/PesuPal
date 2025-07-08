package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.AddReactionDto;
import com.pesupal.server.dto.response.ReactMessageResponseDto;
import com.pesupal.server.model.chat.DirectMessageReaction;

public interface DirectMessageReactionService {

    DirectMessageReaction getDirectMessageReactionById(Long reactionId);

    ReactMessageResponseDto reactToMessage(Long messageId, AddReactionDto addReactionDto);

    void unreactToMessage(Long reactionId, Long userId);
}
