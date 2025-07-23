package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.User;

import java.util.Optional;

public interface GroupChatPinnedService {

    Optional<GroupChatPinned> getPinnedGroupByPinnedByAndGroup(User pinnedBy, Group group);
}
