package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.request.EmailNotificationRequestDto;
import com.pesupal.server.dto.request.org.AddOrgMemberDto;
import com.pesupal.server.dto.response.org.OrgInvitationDto;
import com.pesupal.server.enums.InvitationStatus;
import com.pesupal.server.enums.Role;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgInvitation;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.UserRepository;
import com.pesupal.server.repository.org.OrgInvitationRepository;
import com.pesupal.server.repository.org.OrgMemberRepository;
import com.pesupal.server.service.interfaces.org.OrgConfigurationService;
import com.pesupal.server.service.interfaces.org.OrgInvitationService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import com.pesupal.server.strategies.notification.EmailNotification;
import com.pesupal.server.strategies.notification_template.OrgInvitationTemplate;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrgInvitationServiceImpl implements OrgInvitationService {

    private final UserRepository userRepository;
    private final OrgMemberService orgMemberService;
    private final EmailNotification emailNotification;
    private final OrgInvitationRepository orgInvitationRepository;
    private final OrgConfigurationService orgConfigurationService;
    private final OrgMemberRepository orgMemberRepository;

    /**
     * Saves an organization invitation.
     *
     * @param orgInvitation
     */
    @Override
    public void save(OrgInvitation orgInvitation) {

        orgInvitationRepository.save(orgInvitation);
    }

    /**
     * Checks if a user has already been invited to an organization.
     *
     * @param email
     * @param org
     * @return
     */
    @Override
    public boolean hasAlreadyInvited(String email, Org org) {

        return orgInvitationRepository.existsByEmailAndInviter_OrgAndStatusNot(email, org, InvitationStatus.REVOKED);
    }

    /**
     * Accepts an organization invitation.
     *
     * @param invitationId
     */
    @Override
    public void acceptInvitation(UUID invitationId) {

        OrgInvitation orgInvitation = orgInvitationRepository.findById(invitationId).orElseThrow(() -> new DataNotFoundException("Invitation not found"));

        if (orgInvitation.getStatus().equals(InvitationStatus.ACCEPTED)) {
            throw new ActionProhibitedException("This invitation has already been accepted");
        }

        orgInvitation.setStatus(InvitationStatus.ACCEPTED);
        orgInvitation.setLastUpdatedAt(LocalDateTime.now());
        orgInvitationRepository.save(orgInvitation);

        Optional<User> optionalUser = userRepository.findByEmail(orgInvitation.getEmail());
        optionalUser.ifPresent(user -> orgMemberService.joinInOrg(orgInvitation, user));
    }

    /**
     * Sends an invitation email to the user.
     *
     * @param orgInvitation
     * @param userToInvite
     * @throws MessagingException
     */
    private void sendInvitationEmail(OrgInvitation orgInvitation, String userToInvite) throws MessagingException {

        EmailNotificationRequestDto<OrgInvitationTemplate> emailNotificationRequestDto = new EmailNotificationRequestDto<>();
        emailNotificationRequestDto.setRecipientEmail(userToInvite);
        emailNotificationRequestDto.setTemplate(new OrgInvitationTemplate(orgInvitation));
        emailNotification.sendNotification(emailNotificationRequestDto);
    }

    /**
     * Shares an organization invitation with a user.
     *
     * @param addedBy
     * @param addedBy
     * @throws MessagingException
     */
    @Override
    @Transactional
    public OrgInvitationDto shareInvitation(OrgMember addedBy, AddOrgMemberDto addOrgMemberDto) throws MessagingException {

        Org org = addedBy.getOrg();

        if (!orgConfigurationService.hasPrivilegeToAddMember(org, addedBy.getRole())) {
            throw new PermissionDeniedException("You do not have permission to add members to this organization.");
        }

        String userToAdd = addOrgMemberDto.getEmail().toLowerCase().trim();

        if (orgMemberRepository.existsByUser_EmailAndOrg(userToAdd, org)) {
            throw new ActionProhibitedException("User is already a member of this organization.");
        }

        if (hasAlreadyInvited(userToAdd, org)) {
            throw new ActionProhibitedException("This user has already been invited to join the organization.");
        }

        OrgInvitation orgInvitation = OrgInvitation.builder().inviter(addedBy).status(InvitationStatus.ACTION_PENDING).displayName(addOrgMemberDto.getDisplayName()).email(addOrgMemberDto.getEmail()).invitedAt(LocalDateTime.now()).build();
        orgInvitationRepository.save(orgInvitation);

//        sendInvitationEmail(orgInvitation, email);

        OrgInvitationDto orgInvitationDto = OrgInvitationDto.fromOrgInvitation(orgInvitation);
        orgInvitationDto.setInviter(orgMemberService.getUserPreview(addedBy));
        return orgInvitationDto;
    }

    /**
     * Resends an organization invitation.
     *
     * @param invitationId
     */
    @Override
    public void resendInvitation(UUID invitationId, OrgMember currentOrgMember) throws MessagingException {

        OrgInvitation orgInvitation = orgInvitationRepository.findById(invitationId).orElseThrow(() -> new DataNotFoundException("Invitation not found"));

        if (!orgInvitation.getInviter().getPublicId().equals(currentOrgMember.getPublicId())) {
            throw new PermissionDeniedException("You do not have permission to resend this invitation");
        }

        proceedIfInvitationIsPending(orgInvitation.getStatus());

        sendInvitationEmail(orgInvitation, orgInvitation.getEmail());

        orgInvitation.setInvitedAt(LocalDateTime.now());
        orgInvitationRepository.save(orgInvitation);
    }

    /**
     * Gets all organization invitations by user email.
     *
     * @param email
     * @return
     */
    @Override
    public List<OrgInvitation> getAllOrgInvitationsByUserEmail(String email) {

        return orgInvitationRepository.findAllByEmail(email);
    }

    /**
     * Gets all invitations for the organization member's organization.
     *
     * @param orgMember
     * @return
     */
    @Override
    public List<OrgInvitationDto> getAllInvitations(OrgMember orgMember) {

        if (!orgMember.getRole().equals(Role.SUPER_ADMIN)) {
            throw new PermissionDeniedException("You do not have permission to view invitations");
        }

        return orgInvitationRepository.findAllByInviter_OrgOrderByInvitedAtDesc(orgMember.getOrg()).stream().map(orgInvitation -> {
            OrgInvitationDto dto = OrgInvitationDto.fromOrgInvitation(orgInvitation);
            dto.setInviter(orgMemberService.getUserPreview(orgInvitation.getInviter()));
            return dto;
        }).toList();
    }

    /**
     * Fetch the pending invites in current org
     *
     * @param orgMember
     * @return
     */
    @Override
    public List<OrgInvitationDto> getAllPendingInvitations(OrgMember orgMember) {

        if (!orgMember.getRole().equals(Role.SUPER_ADMIN)) {
            throw new PermissionDeniedException("You do not have permission to view invitations");
        }

        return orgInvitationRepository.findAllByInviter_OrgAndStatusOrderByInvitedAtDesc(orgMember.getOrg(), InvitationStatus.ACTION_PENDING).stream().map(orgInvitation -> {
            OrgInvitationDto dto = OrgInvitationDto.fromOrgInvitation(orgInvitation);
            dto.setInviter(orgMemberService.getUserPreview(orgInvitation.getInviter()));
            return dto;
        }).toList();
    }

    /**
     * Revoke the given invitation.
     *
     * @param invitationId
     * @param currentOrgMember
     */
    @Override
    public void revokeInvitation(UUID invitationId, OrgMember currentOrgMember) {

        OrgInvitation orgInvitation = orgInvitationRepository.findById(invitationId).orElseThrow(() -> new DataNotFoundException("Invitation not found"));

        if (!orgInvitation.getInviter().getPublicId().equals(currentOrgMember.getPublicId())) {
            throw new PermissionDeniedException("You do not have permission to revoke this invitation");
        }

        proceedIfInvitationIsPending(orgInvitation.getStatus());

        orgInvitation.setStatus(InvitationStatus.REVOKED);
        orgInvitation.setLastUpdatedAt(LocalDateTime.now());
        orgInvitationRepository.save(orgInvitation);
    }

    private void proceedIfInvitationIsPending(InvitationStatus status) {

        if (status.equals(InvitationStatus.ACTION_PENDING)) {
            return;
        }

        switch (status) {
            case ACCEPTED -> throw new ActionProhibitedException("This invitation has already been accepted");
            case REVOKED -> throw new ActionProhibitedException("This invitation has already been revoked");
        }
    }
}
