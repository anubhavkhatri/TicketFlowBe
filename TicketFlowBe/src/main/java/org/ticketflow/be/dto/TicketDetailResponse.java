package org.ticketflow.be.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.ticketflow.be.domain.TicketPriority;
import org.ticketflow.be.domain.TicketStatus;

public record TicketDetailResponse(
        UUID id,
        String title,
        String description,
        TicketPriority priority,
        TicketStatus status,
        String assignee,
        Instant createdAt,
        Instant updatedAt,
        List<CommentResponse> comments
) {
}