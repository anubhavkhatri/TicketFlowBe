package org.ticketflow.be.domain;

public class InvalidSortPropertyException extends RuntimeException {

    public InvalidSortPropertyException(String message) {
        super(message);
    }
}