package org.ticketflow.be.dto;

import jakarta.validation.constraints.Size;
import org.ticketflow.be.domain.TicketPriority;
import org.ticketflow.be.domain.TicketStatus;

public record UpdateTicketRequest(
        @Size(max = 200) String title,
        @Size(max = 10000) String description,
        TicketPriority priority,
        TicketStatus status,
        @Size(max = 320) String assignee
) {
}