package com.pesupal.server.service.implementations.group;

import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.GroupChatPinnedRepository;
import com.pesupal.server.service.interfaces.group.GroupChatPinnedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupChatPinnedServiceImpl implements GroupChatPinnedService {

    private final GroupChatPinnedRepository groupChatPinnedRepository;

    /**
     * Retrieves a pinned group by the ID of the user who pinned it, the group ID, and the organization ID.
     *
     * @param pinnedBy
     * @param group
     * @return
     */
    @Override
    public Optional<GroupChatPinned> getPinnedGroupByPinnedByAndGroup(User pinnedBy, Group group) {

        return groupChatPinnedRepository.findByPinnedByAndGroup(pinnedBy, group);
    }
}
