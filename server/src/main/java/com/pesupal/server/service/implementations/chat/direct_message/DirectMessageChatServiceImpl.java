package com.pesupal.server.service.implementations.chat.direct_message;

import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.chat.direct_message.DirectMessageChatRepository;
import com.pesupal.server.service.interfaces.chat.direct_message.DirectMessageChatService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DirectMessageChatServiceImpl implements DirectMessageChatService {

    private final DirectMessageChatRepository directMessageChatRepository;

    /**
     * Find or create the direct message between given users.
     *
     * @param user1
     * @param user2
     * @return
     */
    @Transactional
    @Override
    public DirectMessageChat getOrCreateDirectMessageChat(OrgMember user1, OrgMember user2) {

        if (!user1.getOrg().getId().equals(user2.getOrg().getId())) {
            throw new ActionProhibitedException("Action prohibited. Both of the users are not from same org.");
        }

        if (user1.getId().equals(user2.getId())) {
            return null;    // Same user, so no chat id
        }

        Optional<DirectMessageChat> optionalDirectMessageChat = directMessageChatRepository.findByParticipants(user1.getPublicId(), user2.getPublicId());
        if (optionalDirectMessageChat.isPresent()) {
            return optionalDirectMessageChat.get();
        }
        DirectMessageChat directMessageChat = new DirectMessageChat();
        directMessageChat.setUser1(user1);
        directMessageChat.setUser2(user2);
        return directMessageChatRepository.save(directMessageChat);
    }

    /**
     * Retrieves a direct message chat by its public ID.
     *
     * @param chatId
     * @return
     */
    @Override
    public DirectMessageChat getDirectMessageByPublicId(String chatId) {

        return directMessageChatRepository.findByPublicId(chatId).orElseThrow(() -> new DataNotFoundException("No chat found with the provided ID."));
    }
}
