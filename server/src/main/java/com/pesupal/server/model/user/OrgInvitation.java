package com.pesupal.server.model.user;

import com.pesupal.server.enums.InvitationStatus;
import com.pesupal.server.model.UUIDBaseModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Getter
@SuperBuilder
@Entity
@Setter
@NoArgsConstructor
public class OrgInvitation extends UUIDBaseModel {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String displayName;

    @ManyToOne
    private OrgMember inviter;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    private LocalDateTime invitedAt = LocalDateTime.now();

    private LocalDateTime lastUpdatedAt; // Accepted time or Revoked time
}
