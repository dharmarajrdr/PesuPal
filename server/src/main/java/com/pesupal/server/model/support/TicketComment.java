package com.pesupal.server.model.support;

import com.pesupal.server.model.CreationTimeAuditable;
import com.pesupal.server.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class TicketComment extends CreationTimeAuditable {

    @ManyToOne
    private SupportTicket ticket;

    @ManyToOne
    private User user;

    private boolean isTicketOwner;

    @Column(nullable = false)
    private String message;
}
