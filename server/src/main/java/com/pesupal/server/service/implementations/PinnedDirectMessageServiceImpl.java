package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePinDirectMessageDto;
import com.pesupal.server.dto.request.PinnedDirectMessageDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.chat.PinnedDirectMessage;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.PinnedDirectMessageRepository;
import com.pesupal.server.service.interfaces.OrgMemberService;
import com.pesupal.server.service.interfaces.PinnedDirectMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PinnedDirectMessageServiceImpl implements PinnedDirectMessageService {

    private final OrgMemberService orgMemberService;
    private final PinnedDirectMessageRepository pinnedDirectMessageRepository;

    /**
     * Retrieves all pinned direct messages for the current user in the current organization.
     *
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public List<PinnedDirectMessageDto> getAllPinnedDirectMessages(Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);
        Org org = orgMember.getOrg();
        return pinnedDirectMessageRepository.findAllByOrgIdAndPinnedByIdOrderByOrderIndexAscPinnedUser_IdAsc(userId, orgId).stream().map(pinnedDirectMessage -> {
            OrgMember pinnedUser = orgMemberService.getOrgMemberByUserAndOrg(pinnedDirectMessage.getPinnedUser(), org);
            return PinnedDirectMessageDto.fromUserAndOrgMember(orgMember.getUser(), pinnedUser);
        }).toList();
    }

    /**
     * Retrieves a pinned direct message by the pinnedById, pinnedUserId, and orgId.
     *
     * @param pinnedById
     * @param pinnedUserId
     * @param orgId
     * @return
     */
    @Override
    public PinnedDirectMessage getPinnedDirectMessageByPinnedByIdAndPinnedUserIdAndOrgId(Long pinnedById, Long pinnedUserId, Long orgId) {

        return pinnedDirectMessageRepository.findByPinnedByIdAndPinnedUserIdAndOrgId(pinnedById, pinnedUserId, orgId).orElseThrow(() -> new DataNotFoundException("Pinned direct message not found."));
    }

    /**
     * Retrieves a pinned direct message by its ID.
     *
     * @param id
     * @return
     */
    @Override
    public PinnedDirectMessage getPinnedDirectMessageById(Long id) {

        return pinnedDirectMessageRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Pinned message with ID " + id + " not found."));
    }

    /**
     * Pins a direct message for the current user in the current organization.
     *
     * @param createPinDirectMessageDto
     * @param userId
     * @param orgId
     * @return
     */
    @Override
    public PinnedDirectMessageDto pinDirectMessage(CreatePinDirectMessageDto createPinDirectMessageDto, Long userId, Long orgId) {

        OrgMember orgMember = orgMemberService.getOrgMemberByUserIdAndOrgId(userId, orgId);

        boolean alreadyPinned = isChatPinned(userId, createPinDirectMessageDto.getPinnedUserId(), orgId);
        if (alreadyPinned) {
            throw new ActionProhibitedException("This direct message is already pinned.");
        }

        OrgMember pinnedUser = orgMemberService.getOrgMemberByUserIdAndOrgId(createPinDirectMessageDto.getPinnedUserId(), orgId);
        PinnedDirectMessage pinnedDirectMessage = new PinnedDirectMessage();
        pinnedDirectMessage.setPinnedBy(orgMember.getUser());
        pinnedDirectMessage.setPinnedUser(pinnedUser.getUser());
        pinnedDirectMessage.setOrg(orgMember.getOrg());
        pinnedDirectMessage.setOrderIndex(createPinDirectMessageDto.getOrderIndex());
        pinnedDirectMessageRepository.save(pinnedDirectMessage);
        return PinnedDirectMessageDto.fromUserAndOrgMember(orgMember.getUser(), pinnedUser);
    }

    /**
     * Checks if a chat is pinned for a specific user.
     *
     * @param pinnedById
     * @param pinnedUserId
     * @return
     */
    @Override
    public boolean isChatPinned(Long pinnedById, Long pinnedUserId, Long orgId) {

        return pinnedDirectMessageRepository.existsByPinnedByIdAndPinnedUserIdAndOrgId(pinnedById, pinnedUserId, orgId);
    }

    /**
     * Unpins a direct message for the current user in the current organization.
     *
     * @param id
     * @param userId
     * @param orgId
     */
    @Override
    public void unpinDirectMessage(Long id, Long userId, Long orgId) {

        PinnedDirectMessage pinnedDirectMessage = getPinnedDirectMessageById(id);
        if (!pinnedDirectMessage.getPinnedBy().getId().equals(userId)) {
            throw new PermissionDeniedException("You do not have permission to unpin this direct message.");
        }

        pinnedDirectMessageRepository.delete(pinnedDirectMessage);
    }
}
