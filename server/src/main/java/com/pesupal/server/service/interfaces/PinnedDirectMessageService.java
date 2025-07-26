package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePinDirectMessageDto;
import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.model.chat.PinnedDirectMessage;
import com.pesupal.server.model.user.OrgMember;

import java.util.List;
import java.util.Optional;

public interface PinnedDirectMessageService {

    List<PinnedChatDto> getAllPinnedDirectMessages(OrgMember orgMember);

    Optional<PinnedDirectMessage> getPinnedDirectMessageByPinnedByIdAndPinnedUserIdAndOrgId(Long pinnedById, Long pinnedUserId, Long orgId);

    PinnedDirectMessage getPinnedDirectMessageById(Long id);

    PinnedChatDto pinDirectMessage(CreatePinDirectMessageDto createPinDirectMessageDto, OrgMember orgMember);

    boolean isChatPinned(Long pinnedUserId, OrgMember orgMember);

    void unpinDirectMessage(Long id, OrgMember orgMember);
}
