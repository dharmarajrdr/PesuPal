package com.pesupal.server.repository.support;

import com.pesupal.server.model.support.SupportTicket;
import com.pesupal.server.model.user.OrgMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    Optional<SupportTicket> findByPublicId(String ticketId);

    List<SupportTicket> findByTicketOwnerOrderByCreatedAtDesc(OrgMember orgMember);
}
