package org.ticketflow.be.mapper;

import java.util.List;
import org.ticketflow.be.domain.Comment;
import org.ticketflow.be.domain.Ticket;
import org.ticketflow.be.dto.CommentResponse;
import org.ticketflow.be.dto.TicketDetailResponse;
import org.ticketflow.be.dto.TicketSummaryResponse;

public final class TicketMapper {

    private TicketMapper() {
    }

    public static TicketSummaryResponse toSummary(Ticket ticket) {
        return new TicketSummaryResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getAssignee(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt());
    }

    public static TicketDetailResponse toDetail(Ticket ticket, List<Comment> comments) {
        List<CommentResponse> commentResponses = comments.stream()
                .map(c -> new CommentResponse(c.getId(), c.getBody(), c.getAuthor(), c.getCreatedAt()))
                .toList();
        return new TicketDetailResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getPriority(),
                ticket.getStatus(),
                ticket.getAssignee(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                commentResponses);
    }
}