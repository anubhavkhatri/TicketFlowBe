# Research: Filter Tickets

**Feature**: `007-filter-tickets` | **Date**: 2026-11-20

**Platform baseline**: See [../002-list-tickets/research.md](../002-list-tickets/research.md) (stack, DB, API style).

## Feature decisions

Decision: `status=OPEN&status=IN_PROGRESS`; validate enum values.

**Alternatives considered**: Deferring to platform research for shared choices (WebFlux, GraphQL, cursor pagination).
