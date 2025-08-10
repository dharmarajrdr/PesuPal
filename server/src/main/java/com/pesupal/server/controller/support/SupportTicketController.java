package com.pesupal.server.controller.support;

import com.pesupal.server.dto.request.support.CreateTicketDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.dto.response.support.SupportTicketDto;
import com.pesupal.server.dto.response.support.TicketCommentDto;
import com.pesupal.server.service.interfaces.support.SupportTicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/support")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    @PostMapping("/ticket")
    public ResponseEntity<ApiResponseDto> createTicket(@RequestBody CreateTicketDto createTicketDto) {

        SupportTicketDto supportTicketDto = supportTicketService.createTicket(createTicketDto);
        return ResponseEntity.ok(new ApiResponseDto("Ticket created successfully", supportTicketDto));
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<ApiResponseDto> getTicket(@PathVariable String ticketId) {

        SupportTicketDto supportTicketDto = supportTicketService.getTicket(ticketId);
        return ResponseEntity.ok(new ApiResponseDto("Ticket retrieved successfully", supportTicketDto));
    }

    @GetMapping("/tickets")
    public ResponseEntity<ApiResponseDto> getAllTickets() {

        List<SupportTicketDto> supportTicketDtos = supportTicketService.getAllTickets();
        return ResponseEntity.ok(new ApiResponseDto("Tickets retrieved successfully", supportTicketDtos));
    }

    @GetMapping("/ticket/{ticketId}/comments")
    public ResponseEntity<ApiResponseDto> getTicketComments(@PathVariable String ticketId) {

        List<TicketCommentDto> comments = supportTicketService.getTicketComments(ticketId);
        return ResponseEntity.ok(new ApiResponseDto("Comments retrieved successfully", comments));
    }
}
