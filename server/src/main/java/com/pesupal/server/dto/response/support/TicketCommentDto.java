package com.pesupal.server.dto.response.support;

import com.pesupal.server.model.support.TicketComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketCommentDto {

    private Long id;

    private LocalDateTime createdAt;

    private String message;

    public static TicketCommentDto fromTicketComment(TicketComment ticketComment) {

        TicketCommentDto dto = new TicketCommentDto();
        dto.setId(ticketComment.getId());
        dto.setCreatedAt(ticketComment.getCreatedAt());
        dto.setMessage(ticketComment.getMessage());
        return dto;
    }
}
