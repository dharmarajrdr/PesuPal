package com.pesupal.server.service.interfaces.chat.direct_message;

import com.pesupal.server.dto.response.chat.ReactMessageResponseDto;
import com.pesupal.server.enums.Reaction;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageReaction;

import java.util.Map;

public interface DirectMessageReactionService {

    DirectMessageReaction getDirectMessageReactionById(Long reactionId);

    ReactMessageResponseDto reactToMessage(Long messageId, Reaction reaction);

    void unreactToMessage(Long reactionId);

    Map<Reaction, Integer> getReactionsCountForMessage(DirectMessage directMessage);
}
