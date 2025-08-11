package com.pesupal.server.dto.request.support;

import com.pesupal.server.model.support.SupportTicket;
import com.pesupal.server.model.user.OrgMember;
import lombok.Data;

import java.util.List;

@Data
public class CreateTicketDto {

    private String subject;

    private String description;

    private List<TicketAttachmentDto> attachments;

    public SupportTicket toSupportTicket() {

        SupportTicket ticket = new SupportTicket();
        ticket.setSubject(subject);
        ticket.setDescription(description);
        return ticket;
    }

    public SupportTicket toSupportTicket(OrgMember orgMember) {

        SupportTicket ticket = toSupportTicket();
        ticket.setTicketOwner(orgMember);
        return ticket;
    }
}
