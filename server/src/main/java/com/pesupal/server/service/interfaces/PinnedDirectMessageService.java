package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePinDirectMessageDto;
import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.model.chat.DirectMessageChat;
import com.pesupal.server.model.chat.PinnedDirectMessage;
import com.pesupal.server.model.user.OrgMember;

import java.util.List;
import java.util.Optional;

public interface PinnedDirectMessageService {

    Optional<PinnedDirectMessage> getPinnedDirectMessageByPinnedByAndDirectMessageChat(OrgMember pinnedBy, DirectMessageChat chat);

    List<PinnedChatDto> getAllPinnedDirectMessages();

    PinnedDirectMessage getPinnedDirectMessageById(Long id);

    PinnedChatDto pinDirectMessage(CreatePinDirectMessageDto createPinDirectMessageDto);

    boolean isChatPinned(OrgMember orgMember, String chatId);

    void unpinDirectMessage(Long id, OrgMember orgMember);
}
