package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.dto.request.group.CreatePinGroupChatMessageDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.GroupChatPinnedRepository;
import com.pesupal.server.service.interfaces.group.GroupChatPinnedService;
import com.pesupal.server.service.interfaces.group.GroupService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupChatPinnedServiceImpl extends CurrentValueRetriever implements GroupChatPinnedService {

    private final GroupService groupService;
    private final GroupChatPinnedRepository groupChatPinnedRepository;

    public GroupChatPinnedServiceImpl(@Lazy GroupService groupService, GroupChatPinnedRepository groupChatPinnedRepository) {
        this.groupService = groupService;
        this.groupChatPinnedRepository = groupChatPinnedRepository;
    }

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

    /**
     * Retrieves all pinned group chat messages for the current user and organization.
     *
     * @return
     */
    @Override
    public List<PinnedChatDto> getAllPinnedGroupChatMessages() {

        OrgMember orgMember = getCurrentOrgMember();
        return groupChatPinnedRepository.findAllByPinnedByOrderByOrderIndexAsc(orgMember).stream().map(groupChatPinned -> PinnedChatDto.fromUserAndOrgMemberAndPinnedGroupChatMessage(groupChatPinned)).toList();
    }

    /**
     * Checks if a chat is pinned for a specific user.
     *
     * @param pinnedById
     * @param groupId
     * @return
     */
    @Override
    public boolean isChatPinned(Long pinnedById, Long groupId, Long orgId) {

        return groupChatPinnedRepository.existsByPinnedByIdAndGroupId(pinnedById, groupId);
    }

    /**
     * Pins a group chat message based on the provided DTO and the current user and organization IDs.
     *
     * @param createPinGroupChatMessageDto
     * @return
     */
    @Override
    public PinnedChatDto pinGroupChatMessage(CreatePinGroupChatMessageDto createPinGroupChatMessageDto) {

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getUser().getId();
        Long orgId = orgMember.getOrg().getId();

        boolean alreadyPinned = isChatPinned(userId, createPinGroupChatMessageDto.getPinnedGroupId(), orgId);
        if (alreadyPinned) {
            throw new ActionProhibitedException("This group is already pinned.");
        }

        Group group = groupService.getGroupById(createPinGroupChatMessageDto.getPinnedGroupId());

        GroupChatPinned groupChatPinned = new GroupChatPinned();
        groupChatPinned.setPinnedBy(orgMember);
        groupChatPinned.setGroup(group);
        groupChatPinned.setOrderIndex(createPinGroupChatMessageDto.getOrderIndex());
        groupChatPinnedRepository.save(groupChatPinned);
        return PinnedChatDto.fromUserAndOrgMemberAndPinnedGroupChatMessage(groupChatPinned);
    }

    /**
     * Unpins a group chat message by its ID for the current user and organization.
     *
     * @param id
     */
    @Override
    public void unpinGroupChatMessage(Long id) {

        OrgMember orgMember = getCurrentOrgMember();
        Long userId = orgMember.getUser().getId();

        GroupChatPinned groupChatPinned = groupChatPinnedRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Pinned group chat message with ID " + id + " not found."));
        if (!groupChatPinned.getPinnedBy().getId().equals(userId)) {
            throw new PermissionDeniedException("You do not have permission to unpin this group chat message.");
        }

        groupChatPinnedRepository.delete(groupChatPinned);
    }
}
