package org.ticketflow.be.exception;

import java.util.List;
import org.ticketflow.be.exception.FieldError;

public class BadRequestException extends RuntimeException {

    private final List<FieldError> fieldErrors;

    public BadRequestException(String message) {
        super(message);
        this.fieldErrors = List.of();
    }

    public BadRequestException(String message, List<FieldError> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}