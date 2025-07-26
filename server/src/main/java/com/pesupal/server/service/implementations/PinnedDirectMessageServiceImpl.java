package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePinDirectMessageDto;
import com.pesupal.server.dto.request.PinnedChatDto;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class PinnedDirectMessageServiceImpl implements PinnedDirectMessageService {

    private final OrgMemberService orgMemberService;
    private final PinnedDirectMessageRepository pinnedDirectMessageRepository;

    /**
     * Retrieves all pinned direct messages for the current user in the current organization.
     *
     * @param orgMember
     * @return
     */
    @Override
    public List<PinnedChatDto> getAllPinnedDirectMessages(OrgMember orgMember) {

        Long userId = orgMember.getUser().getPublicId();
        Org org = orgMember.getOrg();
        Long orgId = org.getId();
        return pinnedDirectMessageRepository.findAllByPinnedByIdAndOrgIdOrderByOrderIndexAscPinnedUser_IdAsc(userId, orgId).stream().map(pinnedDirectMessage -> {
            OrgMember pinnedUser = orgMemberService.getOrgMemberByUserAndOrg(pinnedDirectMessage.getPinnedUser(), org);
            return PinnedChatDto.fromUserAndOrgMemberAndPinnedDirectMessage(orgMember.getUser(), pinnedUser, pinnedDirectMessage);
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
    public Optional<PinnedDirectMessage> getPinnedDirectMessageByPinnedByIdAndPinnedUserIdAndOrgId(Long pinnedById, Long pinnedUserId, Long orgId) {

        return pinnedDirectMessageRepository.findByPinnedByIdAndPinnedUserIdAndOrgId(pinnedById, pinnedUserId, orgId);
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
     * @param orgMember
     * @return
     */
    @Override
    public PinnedChatDto pinDirectMessage(CreatePinDirectMessageDto createPinDirectMessageDto, OrgMember orgMember) {

        boolean alreadyPinned = isChatPinned(createPinDirectMessageDto.getPinnedUserId(), orgMember);
        if (alreadyPinned) {
            throw new ActionProhibitedException("This direct message is already pinned.");
        }

        Long orgId = orgMember.getOrg().getId();

        OrgMember pinnedUser = orgMemberService.getOrgMemberByUserIdAndOrgId(createPinDirectMessageDto.getPinnedUserId(), orgId);
        PinnedDirectMessage pinnedDirectMessage = new PinnedDirectMessage();
        pinnedDirectMessage.setPinnedBy(orgMember.getUser());
        pinnedDirectMessage.setPinnedUser(pinnedUser.getUser());
        pinnedDirectMessage.setOrg(orgMember.getOrg());
        pinnedDirectMessage.setOrderIndex(createPinDirectMessageDto.getOrderIndex());
        pinnedDirectMessageRepository.save(pinnedDirectMessage);
        return PinnedChatDto.fromUserAndOrgMemberAndPinnedDirectMessage(orgMember.getUser(), pinnedUser, pinnedDirectMessage);
    }

    /**
     * Checks if a chat is pinned for a specific user.
     *
     * @param orgMember
     * @return
     */
    @Override
    public boolean isChatPinned(Long pinnedUserId, OrgMember orgMember) {

        Long pinnedById = orgMember.getUser().getPublicId();
        Long orgId = orgMember.getOrg().getId();
        return pinnedDirectMessageRepository.existsByPinnedByIdAndPinnedUserIdAndOrgId(pinnedById, pinnedUserId, orgId);
    }

    /**
     * Unpins a direct message for the current user in the current organization.
     *
     * @param id
     * @param orgMember
     */
    @Override
    public void unpinDirectMessage(Long id, OrgMember orgMember) {

        PinnedDirectMessage pinnedDirectMessage = getPinnedDirectMessageById(id);
        Long userId = orgMember.getUser().getPublicId();
        if (!pinnedDirectMessage.getPinnedBy().getId().equals(userId)) {
            throw new PermissionDeniedException("You do not have permission to unpin this direct message.");
        }

        pinnedDirectMessageRepository.delete(pinnedDirectMessage);
    }
}
