package org.ticketflow.be.dto;

import java.util.List;

public record TicketPageResponse(
        List<TicketSummaryResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        String sort
) {
}