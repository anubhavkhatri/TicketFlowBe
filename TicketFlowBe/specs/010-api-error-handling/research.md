# Research: API Error Handling

**Feature**: `010-api-error-handling` | **Date**: 2026-11-20

**Platform baseline**: See [../002-list-tickets/research.md](../002-list-tickets/research.md) (stack, DB, API style).

## Feature decisions

Decision: ProblemDetail optional; use custom ApiError for OpenAPI parity.

**Alternatives considered**: Deferring to platform research for shared choices (WebFlux, GraphQL, cursor pagination).
