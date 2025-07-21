package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreatePinDirectMessageDto;
import com.pesupal.server.dto.request.PinnedDirectMessageDto;
import com.pesupal.server.exceptions.ActionProhibitedException;
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

        boolean alreadyPinned = pinnedDirectMessageRepository.existsByPinnedByIdAndPinnedUserIdAndOrgId(userId, createPinDirectMessageDto.getPinnedUserId(), orgId);
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
}
