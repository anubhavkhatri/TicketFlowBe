# Research: Search Tickets

**Feature**: `006-search-tickets` | **Date**: 2026-11-20

**Platform baseline**: See [../002-list-tickets/research.md](../002-list-tickets/research.md) (stack, DB, API style).

## Feature decisions

Decision: JPA Specification or @Query LIKE; compose with Pageable.

**Alternatives considered**: Deferring to platform research for shared choices (WebFlux, GraphQL, cursor pagination).
