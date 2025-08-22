package com.pesupal.server.dto.request.support;

import com.pesupal.server.model.support.SupportTicket;
import com.pesupal.server.model.support.TicketAttachment;
import lombok.Data;

import java.net.URL;
import java.util.UUID;

@Data
public class TicketAttachmentDto {

    private Long id;

    private String fileName;

    private UUID mediaId;

    private String extension;

    private URL mediaUrl;

    public static TicketAttachmentDto fromTicketAttachment(TicketAttachment ticketAttachment) {

        TicketAttachmentDto ticketAttachmentDto = new TicketAttachmentDto();
        ticketAttachmentDto.setId(ticketAttachment.getId());
        ticketAttachmentDto.setFileName(ticketAttachment.getFileName());
        ticketAttachmentDto.setMediaId(ticketAttachment.getMediaId());
        ticketAttachmentDto.setExtension(ticketAttachment.getExtension());
        return ticketAttachmentDto;
    }

    public TicketAttachment toTicketAttachment() {

        TicketAttachment ticketAttachment = new TicketAttachment();
        ticketAttachment.setFileName(fileName);
        ticketAttachment.setMediaId(mediaId);
        ticketAttachment.setExtension(extension);
        return ticketAttachment;
    }

    public TicketAttachment toTicketAttachment(SupportTicket supportTicket) {

        TicketAttachment ticketAttachment = toTicketAttachment();
        ticketAttachment.setTicket(supportTicket);
        return ticketAttachment;
    }
}
