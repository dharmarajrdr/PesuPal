package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.response.ReactMessageResponseDto;
import com.pesupal.server.enums.Reaction;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageReaction;

import java.util.Map;

public interface DirectMessageReactionService {

    DirectMessageReaction getDirectMessageReactionById(Long reactionId);

    ReactMessageResponseDto reactToMessage(Long messageId, Long userid, Reaction reaction);

    void unreactToMessage(Long reactionId, Long userId);

    Map<Reaction, Integer> getReactionsCountForMessage(DirectMessage directMessage);
}
