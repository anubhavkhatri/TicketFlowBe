package org.ticketflow.be.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
        @NotBlank @Size(max = 5000) String body,
        @NotBlank @Size(max = 320) String author
) {
}