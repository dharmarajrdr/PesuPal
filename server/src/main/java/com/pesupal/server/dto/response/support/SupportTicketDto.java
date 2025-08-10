package com.pesupal.server.dto.response.support;

import com.pesupal.server.model.support.SupportTicket;
import com.pesupal.server.model.support.SupportTicketStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupportTicketDto {

    private LocalDateTime createdAt;

    private String ticketId;

    private String subject;

    private String description;

    private SupportTicketStatus status;
    
    public static SupportTicketDto fromSupportTicket(SupportTicket supportTicket) {

        SupportTicketDto supportTicketDto = new SupportTicketDto();
        supportTicketDto.setCreatedAt(supportTicket.getCreatedAt());
        supportTicketDto.setTicketId(supportTicket.getPublicId());
        supportTicketDto.setSubject(supportTicket.getSubject());
        supportTicketDto.setDescription(supportTicket.getDescription());
        supportTicketDto.setStatus(supportTicket.getStatus());
        return supportTicketDto;
    }
}
