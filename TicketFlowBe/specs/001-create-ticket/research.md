# Research: Create Support Ticket

**Feature**: `001-create-ticket` | **Date**: 2026-11-20

**Platform baseline**: See [../002-list-tickets/research.md](../002-list-tickets/research.md) (stack, DB, API style).

## Feature decisions

Decision: UUID primary keys; jakarta.validation on CreateTicketRequest; status on create limited to OPEN|IN_PROGRESS.

**Alternatives considered**: Deferring to platform research for shared choices (WebFlux, GraphQL, cursor pagination).
