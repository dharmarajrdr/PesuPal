package com.pesupal.server.dto.response.support;

import com.pesupal.server.dto.response.UserPreviewDto;
import com.pesupal.server.model.support.TicketComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketCommentDto {

    private Long id;

    private LocalDateTime createdAt;

    private String message;

    private UserPreviewDto commentedBy;

    public static TicketCommentDto fromTicketComment(TicketComment ticketComment) {

        TicketCommentDto dto = new TicketCommentDto();
        dto.setId(ticketComment.getId());
        dto.setCreatedAt(ticketComment.getCreatedAt());
        dto.setMessage(ticketComment.getMessage());
        if (ticketComment.isTicketOwner()) {
            dto.setCommentedBy(UserPreviewDto.fromOrgMember(ticketComment.getTicket().getTicketOwner()));
        } else {
            dto.setCommentedBy(UserPreviewDto.builder().displayName("PesuPal Support").displayPicture("https://static.vecteezy.com/system/resources/previews/015/911/602/non_2x/customer-support-icon-outline-style-vector.jpg").build());
        }
        return dto;
    }
}
