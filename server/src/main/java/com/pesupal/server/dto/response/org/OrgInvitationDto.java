package com.pesupal.server.dto.response.org;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.enums.InvitationStatus;
import com.pesupal.server.model.user.OrgInvitation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrgInvitationDto {

    private UUID invitationId;

    private String email;

    private String displayName;

    private UserPreviewDto inviter;

    private InvitationStatus status;

    private LocalDateTime invitedAt;

    private LocalDateTime lastUpdatedAt;

    public static OrgInvitationDto fromOrgInvitation(OrgInvitation orgInvitation) {

        OrgInvitationDto dto = new OrgInvitationDto();
        dto.setInvitationId(orgInvitation.getId());
        dto.setEmail(orgInvitation.getEmail());
        dto.setDisplayName(orgInvitation.getDisplayName());
        dto.setStatus(orgInvitation.getStatus());
        dto.setInvitedAt(orgInvitation.getInvitedAt());
        dto.setLastUpdatedAt(orgInvitation.getLastUpdatedAt());
        return dto;
    }
}
