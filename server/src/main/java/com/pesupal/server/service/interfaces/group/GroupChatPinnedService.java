package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.dto.request.group.CreatePinGroupChatMessageDto;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.OrgMember;

import java.util.List;
import java.util.Optional;

public interface GroupChatPinnedService {

    Optional<GroupChatPinned> getPinnedGroupByPinnedByAndGroup(OrgMember pinnedBy, Group group);

    List<PinnedChatDto> getAllPinnedGroupChatMessages();

    boolean isChatPinned(Long pinnedById, Long groupId);

    boolean isChatPinned(Long pinnedById, String groupId);

    PinnedChatDto pinGroupChatMessage(CreatePinGroupChatMessageDto createPinGroupChatMessageDto);

    void unpinGroupChatMessage(String groupChatPublicId);
}
