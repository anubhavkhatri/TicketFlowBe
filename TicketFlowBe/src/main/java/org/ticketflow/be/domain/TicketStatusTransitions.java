package org.ticketflow.be.domain;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import org.ticketflow.be.exception.BadRequestException;
import org.ticketflow.be.exception.FieldError;

/**
 * Enforced lifecycle:
 * OPEN → IN_PROGRESS → RESOLVED → CLOSED
 * OPEN → CANCELLED
 * IN_PROGRESS → CANCELLED
 */
public final class TicketStatusTransitions {

    private static final Map<TicketStatus, Set<TicketStatus>> ALLOWED = Map.of(
            TicketStatus.OPEN, EnumSet.of(TicketStatus.IN_PROGRESS, TicketStatus.CANCELLED),
            TicketStatus.IN_PROGRESS, EnumSet.of(TicketStatus.RESOLVED, TicketStatus.CANCELLED),
            TicketStatus.RESOLVED, EnumSet.of(TicketStatus.CLOSED),
            TicketStatus.CLOSED, EnumSet.noneOf(TicketStatus.class),
            TicketStatus.CANCELLED, EnumSet.noneOf(TicketStatus.class));

    private TicketStatusTransitions() {
    }

    public static void validateTransition(TicketStatus current, TicketStatus target) {
        if (current == target) {
            return;
        }
        Set<TicketStatus> allowed = ALLOWED.get(current);
        if (allowed == null || !allowed.contains(target)) {
            throw new BadRequestException(
                    "Invalid status transition",
                    java.util.List.of(new FieldError(
                            "status",
                            "Cannot transition from " + current + " to " + target + ". Allowed: "
                                    + allowedTargets(current),
                            target)));
        }
    }

    private static String allowedTargets(TicketStatus current) {
        Set<TicketStatus> allowed = ALLOWED.getOrDefault(current, Set.of());
        return allowed.isEmpty() ? "none (terminal state)" : allowed.toString();
    }
}