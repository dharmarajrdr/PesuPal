package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.enums.Reaction;
import com.pesupal.server.model.group.GroupChatMessage;

import java.util.Map;

public interface GroupChatReactionService {

    Map<Reaction, Integer> getReactionsCountForMessage(GroupChatMessage gm);
}
