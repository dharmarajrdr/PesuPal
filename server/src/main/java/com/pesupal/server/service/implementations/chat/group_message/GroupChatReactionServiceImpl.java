package com.pesupal.server.service.implementations.chat.group_message;

import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.dto.response.chat.ReactMessageResponseDto;
import com.pesupal.server.enums.Reaction;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.chat.group_message.Group;
import com.pesupal.server.model.chat.group_message.GroupChatMessage;
import com.pesupal.server.model.chat.group_message.GroupChatReaction;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.ReactionCountProjection;
import com.pesupal.server.repository.chat.group_message.GroupChatReactionRepository;
import com.pesupal.server.service.interfaces.chat.group_message.GroupChatReactionService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GroupChatReactionServiceImpl extends CurrentValueRetriever implements GroupChatReactionService {

    private final GroupChatMessageServiceImpl groupChatMessageService;
    private final GroupChatReactionRepository groupChatReactionRepository;

    public GroupChatReactionServiceImpl(@Lazy GroupChatMessageServiceImpl groupChatMessageService, GroupChatReactionRepository groupChatReactionRepository) {
        this.groupChatMessageService = groupChatMessageService;
        this.groupChatReactionRepository = groupChatReactionRepository;
    }

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

    /**
     * Retrieves a DirectMessageReaction by its ID.
     *
     * @param reactionId
     * @return DirectMessageReaction
     */
    @Override
    public GroupChatReaction getGroupMessageReactionById(Long reactionId) {

        return groupChatReactionRepository.findById(reactionId).orElseThrow(() -> new DataNotFoundException("Reaction with ID " + reactionId + " not found."));
    }

    /**
     * Adds a reaction to a group chat message.
     *
     * @param messageId
     * @param reaction
     * @return
     */
    @Override
    public ReactMessageResponseDto reactToMessage(Long messageId, Reaction reaction) {

        OrgMember reactor = getCurrentOrgMember();
        String orgMemberPublicId = reactor.getPublicId();

        GroupChatMessage groupChatMessage = groupChatMessageService.getGroupChatMessageById(messageId);

        if (Objects.equals(groupChatMessage.getSender().getPublicId(), orgMemberPublicId)) {
            throw new ActionProhibitedException("You cannot react to your own message.");
        }

        if (groupChatMessage.isDeleted()) {
            throw new ActionProhibitedException("Cannot react to a deleted message.");
        }

        GroupChatReaction groupChatReaction = groupChatReactionRepository.findByGroupChatMessageAndReactedBy(groupChatMessage, reactor).orElse(new GroupChatReaction());
        groupChatReaction.setGroupChatMessage(groupChatMessage);
        groupChatReaction.setReactedBy(reactor);
        groupChatReaction.setReaction(reaction);

        groupChatReactionRepository.save(groupChatReaction);

        return new ReactMessageResponseDto(groupChatReaction.getId(), reaction, groupChatReaction.getCreatedAt(), UserBasicInfoDto.fromOrgMember(reactor));
    }

    /**
     * Removes a reaction from a group chat message.
     *
     * @param reactionId
     */
    @Override
    public void unreactToMessage(Long reactionId) {

        GroupChatReaction groupChatReaction = getGroupMessageReactionById(reactionId);

        if (!Objects.equals(groupChatReaction.getReactedBy().getPublicId(), getCurrentOrgMemberPublicId())) {
            throw new ActionProhibitedException("You do not have permission to remove this reaction.");
        }

        groupChatReactionRepository.delete(groupChatReaction);
    }

    /**
     * Deletes all reactions associated with a specific group.
     *
     * @param group
     */
    @Override
    public void deleteAllByGroup(Group group) {

        groupChatReactionRepository.deleteAllByGroupChatMessage_Group(group);
    }
}
