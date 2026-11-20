package org.ticketflow.be.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.ticketflow.be.exception.BadRequestException;

class TicketStatusTransitionsTest {

    @Test
    void allowsMainPath() {
        assertDoesNotThrow(() -> TicketStatusTransitions.validateTransition(TicketStatus.OPEN, TicketStatus.IN_PROGRESS));
        assertDoesNotThrow(() -> TicketStatusTransitions.validateTransition(TicketStatus.IN_PROGRESS, TicketStatus.RESOLVED));
        assertDoesNotThrow(() -> TicketStatusTransitions.validateTransition(TicketStatus.RESOLVED, TicketStatus.CLOSED));
    }

    @Test
    void allowsCancelFromOpenOrInProgress() {
        assertDoesNotThrow(() -> TicketStatusTransitions.validateTransition(TicketStatus.OPEN, TicketStatus.CANCELLED));
        assertDoesNotThrow(() -> TicketStatusTransitions.validateTransition(TicketStatus.IN_PROGRESS, TicketStatus.CANCELLED));
    }

    @Test
    void rejectsClosedToOpen() {
        assertThrows(BadRequestException.class,
                () -> TicketStatusTransitions.validateTransition(TicketStatus.CLOSED, TicketStatus.OPEN));
    }

    @Test
    void rejectsResolvedToOpen() {
        assertThrows(BadRequestException.class,
                () -> TicketStatusTransitions.validateTransition(TicketStatus.RESOLVED, TicketStatus.OPEN));
    }

    @Test
    void rejectsCancelledToOpen() {
        assertThrows(BadRequestException.class,
                () -> TicketStatusTransitions.validateTransition(TicketStatus.CANCELLED, TicketStatus.OPEN));
    }

    @Test
    void rejectsOpenToResolved() {
        assertThrows(BadRequestException.class,
                () -> TicketStatusTransitions.validateTransition(TicketStatus.OPEN, TicketStatus.RESOLVED));
    }
}