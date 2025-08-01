package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.ReactMessageResponseDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.enums.Reaction;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageReaction;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.projections.ReactionCountProjection;
import com.pesupal.server.repository.DirectMessageReactionRepository;
import com.pesupal.server.service.interfaces.DirectMessageReactionService;
import com.pesupal.server.service.interfaces.DirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DirectMessageReactionServiceImpl extends CurrentValueRetriever implements DirectMessageReactionService {

    private final DirectMessageService directMessageService;
    private final DirectMessageReactionRepository directMessageReactionRepository;

    /**
     * Retrieves a DirectMessageReaction by its ID.
     *
     * @param reactionId
     * @return DirectMessageReaction
     */
    @Override
    public DirectMessageReaction getDirectMessageReactionById(Long reactionId) {

        return directMessageReactionRepository.findById(reactionId).orElseThrow(() -> new DataNotFoundException("Reaction with ID " + reactionId + " not found."));
    }

    /**
     * Adds a reaction to a specific message.
     *
     * @param messageId
     * @param reaction
     * @return ReactMessageResponseDto
     */
    @Override
    public ReactMessageResponseDto reactToMessage(Long messageId, Reaction reaction) {

        DirectMessage directMessage = directMessageService.getDirectMessageById(messageId);

        OrgMember reactor = getCurrentOrgMember();

        String orgMemberPublicId = reactor.getPublicId();

        if (Objects.equals(directMessage.getSender().getPublicId(), orgMemberPublicId)) {
            throw new ActionProhibitedException("You cannot react to your own message.");
        }

        if (!Objects.equals(directMessage.getReceiver().getPublicId(), orgMemberPublicId)) {
            throw new PermissionDeniedException("You do not have permission to react to this message.");
        }

        if (directMessage.isDeleted()) {
            throw new ActionProhibitedException("Cannot react to a deleted message.");
        }

        DirectMessageReaction directMessageReaction = directMessageReactionRepository.findByDirectMessageAndReactor(directMessage, reactor).orElse(new DirectMessageReaction());
        directMessageReaction.setDirectMessage(directMessage);
        directMessageReaction.setReactor(reactor);
        directMessageReaction.setReaction(reaction);

        directMessageReaction = directMessageReactionRepository.save(directMessageReaction);

        return new ReactMessageResponseDto(directMessageReaction.getId(), reaction, directMessageReaction.getCreatedAt(), UserBasicInfoDto.fromOrgMember(reactor));

    }

    /**
     * Removes a reaction from a specific message.
     *
     * @param reactionId
     * @return void
     */
    @Override
    public void unreactToMessage(Long reactionId) {

        DirectMessageReaction directMessageReaction = getDirectMessageReactionById(reactionId);

        if (!Objects.equals(directMessageReaction.getReactor().getPublicId(), getCurrentOrgMemberPublicId())) {
            throw new PermissionDeniedException("You do not have permission to remove this reaction.");
        }

        directMessageReactionRepository.delete(directMessageReaction);
    }

    /**
     * Retrieves all reactions for a specific direct message.
     *
     * @param directMessage
     * @return Map
     */
    @Override
    public Map<Reaction, Integer> getReactionsCountForMessage(DirectMessage directMessage) {

        List<ReactionCountProjection> results = directMessageReactionRepository.findReactionCountsByMessageId(directMessage.getId());
        return results.stream().collect(Collectors.toMap(ReactionCountProjection::getReaction, ReactionCountProjection::getCount));
    }

}
