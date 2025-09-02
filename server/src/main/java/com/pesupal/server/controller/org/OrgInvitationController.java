package com.pesupal.server.controller.org;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.org.OrgInvitationDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.service.interfaces.org.OrgInvitationService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/org-invitations")
public class OrgInvitationController extends CurrentValueRetriever {

    private final OrgInvitationService orgInvitationService;

    private static final String LOGIN_PAGE_URL = StaticConfig.CLIENT_DOMAIN + "/signin";

    @GetMapping("/accept/{invitationId}")
    public ResponseEntity<Void> acceptInvitation(@PathVariable UUID invitationId) {

        orgInvitationService.acceptInvitation(invitationId);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(LOGIN_PAGE_URL)).build();
    }

    @PatchMapping("/resend/{invitationId}")
    public ResponseEntity<ApiResponseDto> resendInvitation(@PathVariable UUID invitationId) throws MessagingException {

        orgInvitationService.resendInvitation(invitationId, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Invitation resent successfully"));
    }

    @DeleteMapping("/revoke/{invitationId}")
    public ResponseEntity<ApiResponseDto> revokeInvitation(@PathVariable UUID invitationId) {

        orgInvitationService.revokeInvitation(invitationId, getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Invite revoked successfully"));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllInvitations() {

        List<OrgInvitationDto> invitations = orgInvitationService.getAllInvitations(getCurrentOrgMember());
        return ResponseEntity.ok().body(new ApiResponseDto("Invitations retrieved successfully", invitations));
    }
}
