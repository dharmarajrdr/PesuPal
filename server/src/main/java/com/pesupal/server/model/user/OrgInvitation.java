package com.pesupal.server.model.user;

import com.pesupal.server.model.UUIDBaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    private boolean accepted;

    private LocalDateTime invitedAt = LocalDateTime.now();

    private LocalDateTime acceptedAt;
}
