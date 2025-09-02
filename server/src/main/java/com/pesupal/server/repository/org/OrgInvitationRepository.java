package com.pesupal.server.repository.org;

import com.pesupal.server.enums.InvitationStatus;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.user.OrgInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrgInvitationRepository extends JpaRepository<OrgInvitation, UUID> {

    List<OrgInvitation> findAllByEmail(String email);

    List<OrgInvitation> findAllByInviter_OrgOrderByInvitedAtDesc(Org org);

    boolean existsByEmailAndInviter_OrgAndStatusNot(String email, Org org, InvitationStatus invitationStatus);

    List<OrgInvitation> findAllByInviter_OrgAndStatusOrderByInvitedAtDesc(Org org, InvitationStatus invitationStatus);
}
