package com.pesupal.server.dto.request.support;

import com.pesupal.server.model.support.SupportTicket;
import com.pesupal.server.model.user.OrgMember;

public class CreateTicketDto {

    private String subject;

    private String description;

    public SupportTicket toSupportTicket() {

        SupportTicket ticket = new SupportTicket();
        ticket.setSubject(this.subject);
        ticket.setDescription(this.description);
        return ticket;
    }

    public SupportTicket toSupportTicket(OrgMember orgMember) {

        SupportTicket ticket = toSupportTicket();
        ticket.setTicketOwner(orgMember);
        return ticket;
    }
}
