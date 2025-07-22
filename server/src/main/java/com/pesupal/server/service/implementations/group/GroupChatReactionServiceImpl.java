package com.pesupal.server.service.implementations.group;

import com.pesupal.server.enums.Reaction;
import com.pesupal.server.model.group.GroupChatMessage;
import com.pesupal.server.repository.GroupChatReactionRepository;
import com.pesupal.server.service.interfaces.group.GroupChatReactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class GroupChatReactionServiceImpl implements GroupChatReactionService {

    private final GroupChatReactionRepository groupChatReactionRepository;

    /**
     * Retrieves the count of reactions for a specific group chat message.
     *
     * @param gm
     * @return
     */
    @Override
    public Map<Reaction, Integer> getReactionsCountForMessage(GroupChatMessage gm) {
        return Map.of();
    }
}
