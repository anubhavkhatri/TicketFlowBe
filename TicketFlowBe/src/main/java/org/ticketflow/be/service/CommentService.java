package org.ticketflow.be.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.ticketflow.be.domain.Comment;
import org.ticketflow.be.domain.Ticket;
import org.ticketflow.be.domain.TicketNotFoundException;
import org.ticketflow.be.repository.CommentRepository;
import org.ticketflow.be.repository.TicketRepository;
import org.ticketflow.be.exception.BadRequestException;
import org.ticketflow.be.dto.CreateCommentRequest;
import org.ticketflow.be.exception.FieldError;

@Service
public class CommentService {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;

    public CommentService(TicketRepository ticketRepository, CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    public List<Comment> listForTicket(UUID ticketId) {
        if (!ticketRepository.existsById(ticketId)) {
            throw new TicketNotFoundException(ticketId);
        }
        return commentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }

    @Transactional
    public Comment add(UUID ticketId, CreateCommentRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(ticketId));
        String body = request.body() != null ? request.body().trim() : "";
        if (!StringUtils.hasText(body)) {
            throw new BadRequestException("Validation failed",
                    List.of(new FieldError("body", "body is required", request.body())));
        }
        Comment comment = new Comment(UUID.randomUUID(), ticket, body, request.author().trim(), Instant.now());
        return commentRepository.save(comment);
    }
}