package org.ticketflow.be.service;

import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ticketflow.be.domain.Ticket;
import org.ticketflow.be.domain.TicketNotFoundException;
import org.ticketflow.be.domain.TicketPriority;
import org.ticketflow.be.domain.TicketStatus;
import org.ticketflow.be.domain.TicketStatusTransitions;
import org.ticketflow.be.repository.TicketRepository;
import org.ticketflow.be.exception.BadRequestException;
import org.ticketflow.be.dto.CreateTicketRequest;
import org.ticketflow.be.dto.UpdateTicketRequest;
import org.ticketflow.be.exception.FieldError;

@Service
public class TicketService {

    private static final Set<TicketStatus> CREATE_STATUSES = Set.of(TicketStatus.OPEN, TicketStatus.IN_PROGRESS);

    private final TicketRepository ticketRepository;
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket create(CreateTicketRequest request) {
        String title = trimToNull(request.title());
        if (!StringUtils.hasText(title)) {
            throw new BadRequestException("Validation failed",
                    List.of(new FieldError("title", "title is required", request.title())));
        }
        TicketPriority priority = request.priority() != null ? request.priority() : TicketPriority.MEDIUM;
        TicketStatus status = request.status() != null ? request.status() : TicketStatus.OPEN;
        if (!CREATE_STATUSES.contains(status)) {
            throw new BadRequestException("Validation failed",
                    List.of(new FieldError("status", "status must be OPEN or IN_PROGRESS on create", status)));
        }
        String assignee = trimToNull(request.assignee());
        String description = request.description() != null ? request.description() : "";
        Instant now = Instant.now();
        UUID id = UUID.randomUUID();
        Ticket ticket = new Ticket(id, title, description, priority, status, assignee, now, now);
        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public Ticket getById(UUID id) {
        return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
    }

    @Transactional
    public Ticket update(UUID id, UpdateTicketRequest request) {
        Ticket ticket = getById(id);
        if (request.title() != null) {
            String title = trimToNull(request.title());
            if (!StringUtils.hasText(title)) {
                throw new BadRequestException("Validation failed",
                        List.of(new FieldError("title", "title is required", request.title())));
            }
            ticket.setTitle(title);
        }
        if (request.description() != null) {
            ticket.setDescription(request.description());
        }
        if (request.priority() != null) {
            ticket.setPriority(request.priority());
        }
        if (request.status() != null) {
            TicketStatusTransitions.validateTransition(ticket.getStatus(), request.status());
            ticket.setStatus(request.status());
        }
        if (request.assignee() != null) {
            ticket.setAssignee(trimToNull(request.assignee()));
        }
        ticket.setUpdatedAt(Instant.now());
        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public Page<Ticket> list(String q, List<TicketStatus> statuses, Pageable pageable) {
        String query = q != null ? q.trim() : null;
        if (query != null && query.isEmpty()) {
            throw new BadRequestException("Validation failed",
                    List.of(new FieldError("q", "search query must not be empty", q)));
        }
        Specification<Ticket> spec = buildSpec(query, statuses);
        Pageable effective = applySecondarySort(pageable);
        return ticketRepository.findAll(spec, effective);
    }

    private Specification<Ticket> buildSpec(String q, List<TicketStatus> statuses) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(q)) {
                try {
                    UUID id = UUID.fromString(q);
                    predicates.add(cb.equal(root.get("id"), id));
                } catch (IllegalArgumentException ex) {
                    String pattern = "%" + q.toLowerCase() + "%";
                    predicates.add(cb.or(
                            cb.like(cb.lower(root.get("title")), pattern),
                            cb.like(cb.lower(root.get("description")), pattern)));
                }
            }
            if (statuses != null && !statuses.isEmpty()) {
                predicates.add(root.get("status").in(statuses));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private Pageable applySecondarySort(Pageable pageable) {
        if (pageable.getSort().isSorted() && pageable.getSort().stream()
                .noneMatch(o -> "createdAt".equals(o.getProperty()))) {
            return PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSort().and(Sort.by(Sort.Direction.DESC, "createdAt")));
        }
        if (pageable.getSort().isUnsorted()) {
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        return pageable;
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}