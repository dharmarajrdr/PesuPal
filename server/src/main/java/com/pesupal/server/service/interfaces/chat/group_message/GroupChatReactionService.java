package com.pesupal.server.service.interfaces.chat.group_message;

import com.pesupal.server.enums.Reaction;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;

import java.util.Map;

public interface GroupChatReactionService {

    Map<Reaction, Integer> getReactionsCountForMessage(GroupChatMessage gm);
}
