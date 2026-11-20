package org.ticketflow.be.exception;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.ticketflow.be.domain.InvalidSortPropertyException;
import org.ticketflow.be.domain.TicketNotFoundException;
import org.ticketflow.be.exception.BadRequestException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldError> fields = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldError)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest()
                .body(ApiError.withFields("VALIDATION_FAILED", "Validation failed", fields));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        if (ex.getFieldErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(ApiError.of("BAD_REQUEST", ex.getMessage()));
        }
        return ResponseEntity.badRequest()
                .body(ApiError.withFields("VALIDATION_FAILED", ex.getMessage(), ex.getFieldErrors()));
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(TicketNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.of("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(InvalidSortPropertyException.class)
    public ResponseEntity<ApiError> handleInvalidSort(InvalidSortPropertyException ex) {
        return ResponseEntity.badRequest()
                .body(ApiError.withFields(
                        "VALIDATION_FAILED",
                        ex.getMessage(),
                        List.of(new FieldError("sort", ex.getMessage(), null))));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest()
                .body(ApiError.of("BAD_REQUEST", "Invalid request parameter"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex) {
        String correlationId = UUID.randomUUID().toString();
        log.error("Unexpected error correlationId={}", correlationId, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError("INTERNAL_ERROR", "An unexpected error occurred", correlationId, List.of()));
    }

    private FieldError toFieldError(org.springframework.validation.FieldError fe) {
        return new FieldError(fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue());
    }
}