package com.pesupal.server.service.interfaces.org;

import com.pesupal.server.dto.request.org.AddOrgMemberDto;
import com.pesupal.server.dto.response.org.OrgInvitationDto;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgInvitation;
import com.pesupal.server.model.user.OrgMember;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.UUID;

public interface OrgInvitationService {

    void save(OrgInvitation orgInvitation);

    boolean hasAlreadyInvited(String email, Org org);

    void acceptInvitation(UUID invitationId);

    OrgInvitationDto shareInvitation(OrgMember addedBy, AddOrgMemberDto addOrgMemberDto) throws MessagingException;

    void resendInvitation(UUID invitationId, OrgMember currentOrgMember) throws MessagingException;

    List<OrgInvitation> getAllOrgInvitationsByUserEmail(String email);

    List<OrgInvitationDto> getAllInvitations(OrgMember orgMember);

    void revokeInvitation(UUID invitationId, OrgMember currentOrgMember);

    List<OrgInvitationDto> getAllPendingInvitations(OrgMember currentOrgMember);
}
