package org.ticketflow.be.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ticketflow.be.domain.Comment;
import org.ticketflow.be.domain.InvalidSortPropertyException;
import org.ticketflow.be.domain.Ticket;
import org.ticketflow.be.domain.TicketStatus;
import org.ticketflow.be.service.CommentService;
import org.ticketflow.be.service.TicketService;
import org.ticketflow.be.dto.CommentResponse;
import org.ticketflow.be.dto.CreateCommentRequest;
import org.ticketflow.be.dto.CreateTicketRequest;
import org.ticketflow.be.dto.TicketDetailResponse;
import org.ticketflow.be.dto.TicketPageResponse;
import org.ticketflow.be.dto.TicketSummaryResponse;
import org.ticketflow.be.dto.UpdateTicketRequest;
import org.ticketflow.be.exception.FieldError;
import org.ticketflow.be.exception.BadRequestException;
import org.ticketflow.be.mapper.TicketMapper;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private static final Logger log = LoggerFactory.getLogger(TicketController.class);
    private static final Set<String> ALLOWED_SORT = Set.of("createdAt", "priority", "status", "updatedAt");

    private final TicketService ticketService;
    private final CommentService commentService;

    public TicketController(TicketService ticketService, CommentService commentService) {
        this.ticketService = ticketService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<TicketDetailResponse> create(@Valid @RequestBody CreateTicketRequest request) {
        Ticket ticket = ticketService.create(request);
        TicketDetailResponse body = TicketMapper.toDetail(ticket, List.of());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping
    public TicketPageResponse list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<TicketStatus> status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            validatePageable(pageable);
            Page<Ticket> page = ticketService.list(q, status, pageable);
            List<TicketSummaryResponse> content = page.getContent().stream().map(TicketMapper::toSummary).toList();
            String sort = pageable.getSort().toString();
            return new TicketPageResponse(
                    content,
                    page.getNumber(),
                    page.getSize(),
                    page.getTotalElements(),
                    page.getTotalPages(),
                    sort);
        } catch (RuntimeException ex) {
            log.warn("List tickets failed: {}", ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("/{ticketId}")
    public TicketDetailResponse get(@PathVariable UUID ticketId) {
        Ticket ticket = ticketService.getById(ticketId);
        List<Comment> comments = commentService.listForTicket(ticketId);
        return TicketMapper.toDetail(ticket, comments);
    }

    @PatchMapping("/{ticketId}")
    public TicketDetailResponse update(
            @PathVariable UUID ticketId, @Valid @RequestBody UpdateTicketRequest request) {
        Ticket ticket = ticketService.update(ticketId, request);
        List<Comment> comments = commentService.listForTicket(ticketId);
        return TicketMapper.toDetail(ticket, comments);
    }

    @PostMapping("/{ticketId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable UUID ticketId, @Valid @RequestBody CreateCommentRequest request) {
        Comment comment = commentService.add(ticketId, request);
        CommentResponse body = new CommentResponse(
                comment.getId(), comment.getBody(), comment.getAuthor(), comment.getCreatedAt());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    private void validatePageable(Pageable pageable) {
        if (pageable.getPageSize() < 1 || pageable.getPageSize() > 100) {
            throw new BadRequestException("Validation failed",
                    List.of(new FieldError("size", "size must be between 1 and 100", pageable.getPageSize())));
        }
        if (pageable.getPageNumber() < 0) {
            throw new BadRequestException("Validation failed",
                    List.of(new FieldError("page", "page must be >= 0", pageable.getPageNumber())));
        }
        for (Sort.Order order : pageable.getSort()) {
            if (!ALLOWED_SORT.contains(order.getProperty())) {
                throw new InvalidSortPropertyException(
                        "Invalid sort field; allowed: createdAt, priority, status, updatedAt");
            }
        }
    }
}