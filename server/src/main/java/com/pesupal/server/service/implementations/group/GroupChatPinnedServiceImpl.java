package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.dto.request.group.CreatePinGroupChatMessageDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.GroupChatMemberRepository;
import com.pesupal.server.repository.GroupChatPinnedRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.group.GroupChatPinnedService;
import com.pesupal.server.service.interfaces.group.GroupService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupChatPinnedServiceImpl extends CurrentValueRetriever implements GroupChatPinnedService {

    private final GroupService groupService;
    private final OrgMemberService orgMemberService;
    private final GroupChatPinnedRepository groupChatPinnedRepository;
    private final GroupChatMemberRepository groupChatMemberRepository;

    public GroupChatPinnedServiceImpl(@Lazy GroupService groupService, OrgMemberService orgMemberService, GroupChatPinnedRepository groupChatPinnedRepository, GroupChatMemberRepository groupChatMemberRepository) {
        this.groupService = groupService;
        this.orgMemberService = orgMemberService;
        this.groupChatPinnedRepository = groupChatPinnedRepository;
        this.groupChatMemberRepository = groupChatMemberRepository;
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
        Long userId = orgMember.getId();
        Long orgId = orgMember.getOrg().getId();
        return groupChatPinnedRepository.findAllByPinnedByIdAndGroup_Org_IdOrderByOrderIndexAsc(userId, orgId).stream().map(groupChatPinned -> PinnedChatDto.fromUserAndOrgMemberAndPinnedGroupChatMessage(groupChatPinned)).toList();
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

        OrgMember pinnedBy = getCurrentOrgMember();
        Long userId = pinnedBy.getId();
        Long orgId = pinnedBy.getOrg().getId();

        boolean alreadyPinned = isChatPinned(userId, createPinGroupChatMessageDto.getPinnedGroupId(), orgId);
        if (alreadyPinned) {
            throw new ActionProhibitedException("This group is already pinned.");
        }

        Group group = groupService.getGroupById(createPinGroupChatMessageDto.getPinnedGroupId());

        GroupChatPinned groupChatPinned = new GroupChatPinned();
        groupChatPinned.setPinnedBy(pinnedBy);
        groupChatPinned.setGroup(group);
        groupChatPinned.setOrderIndex(createPinGroupChatMessageDto.getOrderIndex());
        groupChatPinnedRepository.save(groupChatPinned);
        return PinnedChatDto.fromUserAndOrgMemberAndPinnedGroupChatMessage(groupChatPinned);
    }

    /**
     * Unpins a group chat message by its ID for the current user and organization.
     */
    @Override
    public void unpinGroupChatMessage(String groupChatPublicId) {

        OrgMember pinnedBy = getCurrentOrgMember();
        GroupChatPinned groupChatPinned = groupChatPinnedRepository.findByGroup_PublicIdAndPinnedBy(groupChatPublicId, pinnedBy).orElseThrow(() -> new DataNotFoundException("You have not pinned this group yet."));
        groupChatPinnedRepository.delete(groupChatPinned);
    }
}
