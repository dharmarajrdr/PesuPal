package com.pesupal.server.service.interfaces.support;

import com.pesupal.server.dto.request.support.CreateTicketDto;
import com.pesupal.server.dto.response.support.SupportTicketDto;
import com.pesupal.server.dto.response.support.TicketCommentDto;

import java.util.List;

public interface SupportTicketService {

    SupportTicketDto createTicket(CreateTicketDto createTicketDto);

    List<TicketCommentDto> getTicketComments(String ticketId);

    SupportTicketDto getTicket(String ticketId);

    List<SupportTicketDto> getAllTickets();
}
