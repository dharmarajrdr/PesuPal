package com.pesupal.server.model.support;

import com.pesupal.server.model.PublicAccessModel;
import com.pesupal.server.model.user.OrgMember;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class SupportTicket extends PublicAccessModel {

    @ManyToOne
    private OrgMember ticketOwner;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, length = 999)
    private String description;

    @Enumerated(EnumType.STRING)
    private SupportTicketStatus status;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketComment> comments;
}
