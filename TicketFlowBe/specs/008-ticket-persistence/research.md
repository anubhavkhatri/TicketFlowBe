# Research: Ticket and Comment Persistence

**Feature**: `008-ticket-persistence` | **Date**: 2026-11-20

**Platform baseline**: See [../002-list-tickets/research.md](../002-list-tickets/research.md) (stack, DB, API style).

## Feature decisions

Decision: Flyway V1 tickets V2 comments; ddl-auto=validate in prod.

**Alternatives considered**: Deferring to platform research for shared choices (WebFlux, GraphQL, cursor pagination).
