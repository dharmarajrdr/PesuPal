package com.pesupal.server.service.implementations.group;

import com.pesupal.server.enums.Reaction;
import com.pesupal.server.model.group.GroupChatMessage;
import com.pesupal.server.projections.ReactionCountProjection;
import com.pesupal.server.repository.GroupChatReactionRepository;
import com.pesupal.server.service.interfaces.group.GroupChatReactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupChatReactionServiceImpl implements GroupChatReactionService {

    private final GroupChatReactionRepository groupChatReactionRepository;

    /**
     * Retrieves the count of reactions for a specific group chat message.
     *
     * @param groupChatMessage
     * @return
     */
    @Override
    public Map<Reaction, Integer> getReactionsCountForMessage(GroupChatMessage groupChatMessage) {

        List<ReactionCountProjection> results = groupChatReactionRepository.findReactionCountsByMessageId(groupChatMessage.getId());
        return results.stream().collect(Collectors.toMap(ReactionCountProjection::getReaction, ReactionCountProjection::getCount));
    }
}
