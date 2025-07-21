package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreatePinDirectMessageDto;
import com.pesupal.server.dto.request.PinnedDirectMessageDto;
import com.pesupal.server.model.chat.PinnedDirectMessage;

import java.util.List;

public interface PinnedDirectMessageService {

    List<PinnedDirectMessageDto> getAllPinnedDirectMessages(Long currentUserId, Long currentOrgId);

    PinnedDirectMessage getPinnedDirectMessageByPinnedByIdAndPinnedUserIdAndOrgId(Long pinnedById, Long pinnedUserId, Long orgId);

    PinnedDirectMessage getPinnedDirectMessageById(Long id);

    PinnedDirectMessageDto pinDirectMessage(CreatePinDirectMessageDto createPinDirectMessageDto, Long currentUserId, Long currentOrgId);

    boolean isChatPinned(Long pinnedById, Long pinnedUserId, Long orgId);

    void unpinDirectMessage(Long id, Long userid, Long orgId);
}
