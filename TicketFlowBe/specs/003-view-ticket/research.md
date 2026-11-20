# Research: View Ticket

**Feature**: `003-view-ticket` | **Date**: 2026-11-20

**Platform baseline**: See [../002-list-tickets/research.md](../002-list-tickets/research.md) (stack, DB, API style).

## Feature decisions

Decision: Single round-trip embeds comments[]; 404 ApiError when missing.

**Alternatives considered**: Deferring to platform research for shared choices (WebFlux, GraphQL, cursor pagination).
