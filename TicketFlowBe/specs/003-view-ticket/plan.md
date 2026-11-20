# Implementation Plan: View Ticket

**Branch**: `003-view-ticket` | **Date**: 2026-11-20 | **Spec**: [spec.md](./spec.md)

**Platform stack**: Java 21, Spring Boot, PostgreSQL/H2, REST, React/Next.js client, Cursor, GitHub Copilot

## Summary

Implement `GET /api/v1/tickets/{ticketId}` returning TicketDetail with comments ascending by createdAt.

## Technical Context

**Language/Version**: Java 21
**Primary Dependencies**: Spring Boot 4.x Web MVC, Spring Data JPA, Bean Validation, PostgreSQL, H2 (test), springdoc-openapi
**Storage**: PostgreSQL (`dev`/`prod`); H2 (`test` profile only)
**Testing**: JUnit 5, Spring Boot Test, MockMvc
**Target Platform**: JVM server; React/Next.js client (separate repo)
**Project Type**: Web service (this repo) + SPA client
**Constraints**: Constitution layered architecture; REST + JSON; AI tools Cursor/Copilot


## Constitution Check

*GATE: passed pre/post design*

| Principle | Status | Notes |
|-----------|--------|--------|
| I. SDD | PASS | Tied to this spec.md |
| II. REST contract | PASS | See contracts/ |
| III. Tests | PASS | MockMvc + H2 in quickstart |
| IV. Layers | PASS | Controller → Service → Repository |
| V. Simplicity | PASS | YAGNI per spec scope |


## Project Structure

Documentation under `specs/003-view-ticket/`. Source: `src/main/java/org/ticketflow/be/` per [002-list-tickets/plan.md](../002-list-tickets/plan.md).

**Depends on**: Implement `008-ticket-persistence` before feature endpoints unless already merged from `002-list-tickets` tasks.

## Phase 0 & 1 Artifacts

| Artifact | Path |
|----------|------|
| Research | [research.md](./research.md) |
| Data model | [data-model.md](./data-model.md) |
| Contract ref | [contracts/](./contracts/) |
| Quickstart | [quickstart.md](./quickstart.md) |

## API / scope

**Primary contract**: GET /api/v1/tickets/{ticketId} (`getTicket`)

Canonical OpenAPI: [../002-list-tickets/contracts/openapi.yaml](../002-list-tickets/contracts/openapi.yaml)

## Complexity Tracking

No unjustified constitution violations.
