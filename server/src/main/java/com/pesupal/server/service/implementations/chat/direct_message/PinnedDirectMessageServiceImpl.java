package com.pesupal.server.service.implementations.chat.direct_message;

import com.pesupal.server.dto.request.chat.direct_message.CreatePinDirectMessageDto;
import com.pesupal.server.dto.request.chat.direct_message.PinnedChatDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.chat.direct_message.DirectMessageChat;
import com.pesupal.server.model.chat.direct_message.PinnedDirectMessage;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.chat.direct_message.PinnedDirectMessageRepository;
import com.pesupal.server.service.interfaces.MediaService;
import com.pesupal.server.service.interfaces.chat.direct_message.DirectMessageChatService;
import com.pesupal.server.service.interfaces.chat.direct_message.PinnedDirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PinnedDirectMessageServiceImpl extends CurrentValueRetriever implements PinnedDirectMessageService {

    private final MediaService mediaService;
    private final DirectMessageChatService directMessageChatService;
    private final PinnedDirectMessageRepository pinnedDirectMessageRepository;

    /**
     * @param pinnedBy
     * @return
     */
    @Override
    public Optional<PinnedDirectMessage> getPinnedDirectMessageByPinnedByAndDirectMessageChat(OrgMember pinnedBy, DirectMessageChat chat) {

        return pinnedDirectMessageRepository.findByPinnedByAndChat(pinnedBy, chat);
    }

    /**
     * Retrieves all pinned direct messages for the current user in the current organization.
     *
     * @return
     */
    @Override
    public List<PinnedChatDto> getAllPinnedDirectMessages() {

        OrgMember orgMember = getCurrentOrgMember();
        return pinnedDirectMessageRepository.findAllByPinnedByOrderByOrderIndexAsc(orgMember).stream().map(pinnedDirectMessage -> {
            PinnedChatDto pinnedChatDto = PinnedChatDto.fromPinnedDirectMessage(pinnedDirectMessage);
            DirectMessageChat pinnedChat = pinnedDirectMessage.getChat();
            OrgMember pinnedByUser = pinnedDirectMessage.getPinnedBy();
            OrgMember pinnedUser = pinnedChat.getAnotherUser(pinnedByUser);
            pinnedChatDto.setDisplayPicture(mediaService.generatePresignedUrl(pinnedUser.getDisplayPicture()));
            return pinnedChatDto;
        }).toList();
    }

    /**
     * Retrieves a pinned direct message by its ID.
     *
     * @param id
     * @return
     */
    @Override
    public PinnedDirectMessage getPinnedDirectMessageById(Long id) {

        return pinnedDirectMessageRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Pinned message with ID " + id + " not found."));
    }

    /**
     * Pins a direct message for the current user in the current organization.
     *
     * @param createPinDirectMessageDto
     * @return
     */
    @Override
    public PinnedChatDto pinDirectMessage(CreatePinDirectMessageDto createPinDirectMessageDto) {

        OrgMember orgMember = getCurrentOrgMember();

        String chatId = createPinDirectMessageDto.getChatId();

        boolean alreadyPinned = isChatPinned(orgMember, chatId);
        if (alreadyPinned) {
            throw new ActionProhibitedException("This direct message is already pinned.");
        }

        DirectMessageChat directMessageChat = directMessageChatService.getDirectMessageByPublicId(chatId);

        PinnedDirectMessage pinnedDirectMessage = new PinnedDirectMessage();
        pinnedDirectMessage.setPinnedBy(orgMember);
        pinnedDirectMessage.setChat(directMessageChat);
        pinnedDirectMessage.setOrderIndex(createPinDirectMessageDto.getOrderIndex());
        pinnedDirectMessageRepository.save(pinnedDirectMessage);
        PinnedChatDto pinnedChatDto = PinnedChatDto.fromPinnedDirectMessage(pinnedDirectMessage);
        DirectMessageChat pinnedChat = pinnedDirectMessage.getChat();
        OrgMember pinnedByUser = pinnedDirectMessage.getPinnedBy();
        OrgMember pinnedUser = pinnedChat.getAnotherUser(pinnedByUser);
        pinnedChatDto.setDisplayPicture(mediaService.generatePresignedUrl(pinnedUser.getDisplayPicture()));
        return pinnedChatDto;
    }

    /**
     * Checks if a chat is pinned for a specific user.
     *
     * @return
     */
    @Override
    public boolean isChatPinned(OrgMember orgMember, String chatId) {

        return pinnedDirectMessageRepository.existsByPinnedByAndChat_PublicId(orgMember, chatId);
    }

    /**
     * Unpins a direct message for the current user in the current organization.
     *
     * @param id
     * @param orgMember
     */
    @Override
    public void unpinDirectMessage(Long id, OrgMember orgMember) {

        PinnedDirectMessage pinnedDirectMessage = getPinnedDirectMessageById(id);
        Long userId = orgMember.getId();
        if (!pinnedDirectMessage.getPinnedBy().getId().equals(userId)) {
            throw new PermissionDeniedException("You do not have permission to unpin this direct message.");
        }

        pinnedDirectMessageRepository.delete(pinnedDirectMessage);
    }
}
