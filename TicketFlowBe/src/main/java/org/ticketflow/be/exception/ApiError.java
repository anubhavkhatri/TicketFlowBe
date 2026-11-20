package org.ticketflow.be.exception;

import java.util.List;

public record ApiError(String code, String message, String correlationId, List<FieldError> fieldErrors) {

    public static ApiError of(String code, String message) {
        return new ApiError(code, message, null, List.of());
    }

    public static ApiError withFields(String code, String message, List<FieldError> fieldErrors) {
        return new ApiError(code, message, null, fieldErrors);
    }
}