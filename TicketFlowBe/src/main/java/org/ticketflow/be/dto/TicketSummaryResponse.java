package org.ticketflow.be.dto;

import java.time.Instant;
import java.util.UUID;
import org.ticketflow.be.domain.TicketPriority;
import org.ticketflow.be.domain.TicketStatus;

public record TicketSummaryResponse(
        UUID id,
        String title,
        TicketPriority priority,
        TicketStatus status,
        String assignee,
        Instant createdAt,
        Instant updatedAt
) {
}