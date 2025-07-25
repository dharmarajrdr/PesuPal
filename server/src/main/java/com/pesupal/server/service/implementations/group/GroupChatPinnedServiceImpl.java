package com.pesupal.server.service.implementations.group;

import com.pesupal.server.dto.request.PinnedChatDto;
import com.pesupal.server.dto.request.group.CreatePinGroupChatMessageDto;
import com.pesupal.server.model.group.Group;
import com.pesupal.server.model.group.GroupChatPinned;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.GroupChatPinnedRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.group.GroupChatPinnedService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupChatPinnedServiceImpl implements GroupChatPinnedService {

    private final GroupChatPinnedRepository groupChatPinnedRepository;
    private final OrgMemberService orgMemberService;

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
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<PinnedChatDto> getAllPinnedGroupChatMessages(Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        Org org = orgMember.getOrg();
        return groupChatPinnedRepository.findAllByPinnedByIdAndGroup_Org_IdOrderByOrderIndexAsc(userId, orgId).stream().map(groupChatPinned -> {
            return PinnedChatDto.fromUserAndOrgMemberAndPinnedGroupChatMessage(groupChatPinned);
        }).toList();
    }

    /**
     * Pins a group chat message based on the provided DTO and the current user and organization IDs.
     *
     * @param createPinGroupChatMessageDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public PinnedChatDto pinGroupChatMessage(CreatePinGroupChatMessageDto createPinGroupChatMessageDto, Long userId, Long orgId) {

        return null;
    }

    /**
     * Unpins a group chat message by its ID for the current user and organization.
     *
     * @param id
     * @param currentUserId
     * @param currentOrgId
     */
    @Override
    public void unpinGroupChatMessage(Long id, Long currentUserId, Long currentOrgId) {

    }
}
