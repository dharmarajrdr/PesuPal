package com.pesupal.server.service.interfaces.org;

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

    void shareInvitation(OrgMember addedBy, String email, String displayName) throws MessagingException;

    void resendInvitation(UUID invitationId, OrgMember currentOrgMember) throws MessagingException;

    List<OrgInvitation> getAllOrgInvitationsByUserEmail(String email);

    List<OrgInvitationDto> getAllInvitations(OrgMember orgMember);
}
