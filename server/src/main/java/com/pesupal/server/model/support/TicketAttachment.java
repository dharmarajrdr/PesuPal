package com.pesupal.server.model.support;

import com.pesupal.server.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class TicketAttachment extends BaseModel {

    @ManyToOne
    private SupportTicket ticket;

    @Column(nullable = false, unique = true)
    private UUID mediaId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String extension;
}
