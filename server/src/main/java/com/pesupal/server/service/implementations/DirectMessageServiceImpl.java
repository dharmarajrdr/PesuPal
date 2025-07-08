package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.GetConversationBetweenUsers;
import com.pesupal.server.dto.response.DirectMessageResponseDto;
import com.pesupal.server.model.chat.DirectMessage;
import com.pesupal.server.repository.DirectMessageRepository;
import com.pesupal.server.service.interfaces.DirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DirectMessageServiceImpl implements DirectMessageService {

    private final DirectMessageRepository directMessageRepository;

    /**
     * Retrieves direct messages between two users by their IDs.
     *
     * @param getConversationBetweenUsers
     * @return List of DirectMessageResponseDto
     */
    @Override
    public List<DirectMessageResponseDto> getDirectMessagesBetweenUsers(GetConversationBetweenUsers getConversationBetweenUsers) {

        Pageable pageable = PageRequest.of(getConversationBetweenUsers.getPage(), getConversationBetweenUsers.getSize(), Sort.by("createdAt").descending());
        Page<DirectMessage> messages = directMessageRepository.findByChatId(getConversationBetweenUsers.getChatId(), pageable);
        return messages.stream().map(DirectMessageResponseDto::fromDirectMessage).toList();
    }
}
