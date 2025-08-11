package com.pesupal.server.service.implementations.support;

import com.pesupal.server.dto.request.support.CreateTicketDto;
import com.pesupal.server.dto.response.support.SupportTicketDto;
import com.pesupal.server.dto.response.support.TicketCommentDto;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.support.SupportTicket;
import com.pesupal.server.model.support.TicketAttachment;
import com.pesupal.server.model.support.TicketComment;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.support.SupportTicketRepository;
import com.pesupal.server.repository.support.TicketAttachmentRepository;
import com.pesupal.server.service.interfaces.support.SupportTicketService;
import com.pesupal.server.strategies.media_storage.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SupportTicketServiceImpl extends CurrentValueRetriever implements SupportTicketService {

    private final S3Service s3Service;
    private final SupportTicketRepository supportTicketRepository;
    private final TicketAttachmentRepository ticketAttachmentRepository;

    /**
     * Creates a new support ticket.
     *
     * @param createTicketDto
     * @return
     */
    @Override
    public SupportTicketDto createTicket(CreateTicketDto createTicketDto) {

        OrgMember orgMember = getCurrentOrgMember();
        SupportTicket supportTicket = createTicketDto.toSupportTicket(orgMember);
        supportTicketRepository.save(supportTicket);
        List<TicketAttachment> ticketAttachments = createTicketDto.getAttachments().stream().map(ticketAttachmentDto -> ticketAttachmentDto.toTicketAttachment(supportTicket)).toList();
        ticketAttachmentRepository.saveAll(ticketAttachments);
        return SupportTicketDto.fromSupportTicket(supportTicket);
    }

    /**
     * Retrieves a list of support tickets for the current organization member.
     *
     * @param ticketId
     * @return
     */
    @Override
    public List<TicketCommentDto> getTicketComments(String ticketId) {

        OrgMember orgMember = getCurrentOrgMember();
        SupportTicket supportTicket = supportTicketRepository.findByPublicId(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        if (!supportTicket.getTicketOwner().getId().equals(orgMember.getId())) {
            throw new PermissionDeniedException("You do not have permission to access this ticket.");
        }

        List<TicketComment> ticketComments = supportTicket.getComments();

        ticketComments.sort((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()));

        return ticketComments.stream().map(TicketCommentDto::fromTicketComment).toList();
    }

    /**
     * Retrieves a support ticket by its ID.
     *
     * @param ticketId
     * @return
     */
    @Override
    public SupportTicketDto getTicket(String ticketId) {

        OrgMember orgMember = getCurrentOrgMember();
        SupportTicket supportTicket = supportTicketRepository.findByPublicId(ticketId).orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
        if (!supportTicket.getTicketOwner().getId().equals(orgMember.getId())) {
            throw new PermissionDeniedException("You do not have permission to access this ticket.");
        }
        SupportTicketDto supportTicketDto = SupportTicketDto.fromSupportTicket(supportTicket);
        supportTicketDto.setAttachments(supportTicketDto.getAttachments().stream().peek(ticketAttachmentDto -> {
            String key = ticketAttachmentDto.getMediaId() + ticketAttachmentDto.getExtension();
            ticketAttachmentDto.setMediaUrl(s3Service.generatePresignedUrl(key));
        }).toList());
        return supportTicketDto;
    }

    /**
     * Retrieves all support tickets for the current organization member.
     *
     * @return
     */
    @Override
    public List<SupportTicketDto> getAllTickets() {

        return supportTicketRepository.findByTicketOwnerOrderByCreatedAtDesc(getCurrentOrgMember()).stream().map(supportTicket -> {
            SupportTicketDto supportTicketDto = SupportTicketDto.fromSupportTicket(supportTicket);
            supportTicketDto.setAttachments(supportTicketDto.getAttachments().stream().peek(ticketAttachmentDto -> {
                String key = ticketAttachmentDto.getMediaId() + ticketAttachmentDto.getExtension();
                ticketAttachmentDto.setMediaUrl(s3Service.generatePresignedUrl(key));
            }).toList());
            return supportTicketDto;
        }).toList();
    }
}
