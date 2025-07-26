package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.dto.request.group.CreatePinGroupChatMessageDto;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.User;

import java.util.List;
import java.util.Optional;

public interface GroupChatPinnedService {

    Optional<GroupChatPinned> getPinnedGroupByPinnedByAndGroup(User pinnedBy, Group group);

    List<PinnedChatDto> getAllPinnedGroupChatMessages(Long currentUserId, Long currentOrgId);

    boolean isChatPinned(Long pinnedById, Long pinnedUserId, Long orgId);

    PinnedChatDto pinGroupChatMessage(CreatePinGroupChatMessageDto createPinGroupChatMessageDto, Long currentUserId, Long currentOrgId);

    void unpinGroupChatMessage(Long id, Long currentUserId, Long currentOrgId);
}
