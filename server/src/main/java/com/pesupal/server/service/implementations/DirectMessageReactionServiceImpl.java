package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.AddReactionDto;
import com.pesupal.server.dto.response.ReactMessageResponseDto;
import com.pesupal.server.dto.response.UserBasicInfoDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.model.chat.DirectMessageReaction;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.DirectMessageReactionRepository;
import com.pesupal.server.service.interfaces.DirectMessageReactionService;
import com.pesupal.server.service.interfaces.DirectMessageService;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DirectMessageReactionServiceImpl implements DirectMessageReactionService {

    private final UserService userService;
    private final OrgMemberService orgMemberService;
    private final DirectMessageService directMessageService;
    private final DirectMessageReactionRepository directMessageReactionRepository;

    public Optional<DirectMessageReaction> getDirectMessageReactionByMessageIdAndUser(DirectMessage directMessage, User user) {

        return directMessageReactionRepository.findByDirectMessageAndUser(directMessage, user);
    }

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
     * @param addReactionDto
     */
    @Override
    public ReactMessageResponseDto reactToMessage(Long messageId, AddReactionDto addReactionDto) {

        DirectMessage directMessage = directMessageService.getDirectMessageById(messageId);

        if (Objects.equals(directMessage.getSender().getId(), addReactionDto.getUserId())) {
            throw new ActionProhibitedException("You cannot react to your own message.");
        }

        if (!Objects.equals(directMessage.getReceiver().getId(), addReactionDto.getUserId())) {
            throw new PermissionDeniedException("You do not have permission to react to this message.");
        }

        if (directMessage.isDeleted()) {
            throw new ActionProhibitedException("Cannot react to a deleted message.");
        }

        User reactor = userService.getUserById(addReactionDto.getUserId());

        DirectMessageReaction directMessageReaction = getDirectMessageReactionByMessageIdAndUser(directMessage, reactor).orElse(new DirectMessageReaction());
        directMessageReaction.setDirectMessage(directMessage);
        directMessageReaction.setUser(reactor);
        directMessageReaction.setReaction(addReactionDto.getReaction());

        directMessageReaction = directMessageReactionRepository.save(directMessageReaction);

        Org org = directMessage.getOrg();
        OrgMember orgMember = orgMemberService.getOrgMemberByUserAndOrg(reactor, org);

        return new ReactMessageResponseDto(directMessageReaction.getId(), addReactionDto.getReaction(), directMessageReaction.getCreatedAt(), UserBasicInfoDto.fromOrgMember(orgMember));

    }

    /**
     * Removes a reaction from a specific message.
     *
     * @param reactionId
     * @param userId
     * @return
     */
    @Override
    public void unreactToMessage(Long reactionId, Long userId) {

        DirectMessageReaction directMessageReaction = getDirectMessageReactionById(reactionId);

        if (!Objects.equals(directMessageReaction.getUser().getId(), userId)) {
            throw new PermissionDeniedException("You do not have permission to remove this reaction.");
        }

        directMessageReactionRepository.delete(directMessageReaction);
    }
}
