package com.pesupal.server.service.interfaces.chat.group_message;

import com.pesupal.server.dto.response.chat.ReactMessageResponseDto;
import com.pesupal.server.enums.Reaction;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.chat.group_message.GroupChatReaction;

import java.util.Map;

public interface GroupChatReactionService {

    Map<Reaction, Integer> getReactionsCountForMessage(GroupChatMessage gm);

    GroupChatReaction getGroupMessageReactionById(Long reactionId);

    ReactMessageResponseDto reactToMessage(Long messageId, Reaction reaction);

    void unreactToMessage(Long reactionId);
}
