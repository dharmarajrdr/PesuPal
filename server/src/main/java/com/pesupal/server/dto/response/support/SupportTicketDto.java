package com.pesupal.server.dto.response.support;

import com.pesupal.server.dto.request.support.TicketAttachmentDto;
import com.pesupal.server.model.support.SupportTicket;
import com.pesupal.server.model.support.SupportTicketStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SupportTicketDto {

    private LocalDateTime createdAt;

    private String ticketId;

    private String subject;

    private String description;

    private SupportTicketStatus status;

    private List<TicketAttachmentDto> attachments;

    public static SupportTicketDto fromSupportTicket(SupportTicket supportTicket) {

        SupportTicketDto supportTicketDto = new SupportTicketDto();
        supportTicketDto.setCreatedAt(supportTicket.getCreatedAt());
        supportTicketDto.setTicketId(supportTicket.getPublicId());
        supportTicketDto.setSubject(supportTicket.getSubject());
        supportTicketDto.setDescription(supportTicket.getDescription());
        supportTicketDto.setStatus(supportTicket.getStatus());
        if (supportTicket.getAttachments() != null) {
            supportTicketDto.setAttachments(supportTicket.getAttachments().stream().map(TicketAttachmentDto::fromTicketAttachment).toList());
        }
        return supportTicketDto;
    }
}
