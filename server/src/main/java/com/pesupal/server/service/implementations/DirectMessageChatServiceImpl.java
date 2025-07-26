package com.pesupal.server.service.implementations;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.chat.DirectMessageChat;
import com.pesupal.server.repository.DirectMessageChatRepository;
import com.pesupal.server.service.interfaces.DirectMessageChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DirectMessageChatServiceImpl implements DirectMessageChatService {

    private final DirectMessageChatRepository directMessageChatRepository;

    /**
     * @param orgMemberPublicId1
     * @param orgMemberPublicId2
     * @return
     */
    @Override
    public DirectMessageChat getDirectMessageChat(String orgMemberPublicId1, String orgMemberPublicId2) {

        return directMessageChatRepository.findByParticipants(orgMemberPublicId1, orgMemberPublicId2).orElseThrow(() -> new DataNotFoundException("No chat found yet."));
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
