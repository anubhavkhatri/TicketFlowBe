# Research: TicketFlow List & Platform Stack

**Feature**: `002-list-tickets`  
**Date**: 2026-11-20

## 1. Backend runtime and framework

**Decision**: Java 21 with Spring Boot 4.x (current parent in `pom.xml`), Spring Web MVC,
Spring Data JPA, Jakarta Bean Validation.

**Rationale**: Matches constitution and user stack; existing Maven module; MockMvc for API
tests; mature pagination/sorting via `Pageable`.

**Alternatives considered**:

- **WebFlux**: Rejected for v1—team-scale synchronous CRUD does not need reactive stack.
- **JDBC template without JPA**: Rejected—more boilerplate for relations (ticket ↔ comments).

## 2. Database strategy

**Decision**:

- **Production / local dev**: PostgreSQL via Spring profile `dev` or `prod`.
- **Tests**: H2 in-memory with profile `test` (constitution requirement).
- **Schema**: Hibernate `ddl-auto=validate` in prod; `create-drop` or Flyway in plan tasks
  (recommend Flyway for repeatable migrations in tasks phase).

**Rationale**: Constitution mandates PostgreSQL for non-test; H2 only under test profile.

**Alternatives considered**:

- **H2 for local dev default**: Allowed as optional profile but production path must be
  PostgreSQL-documented in `quickstart.md`.

## 3. API style and documentation

**Decision**: REST JSON under `/api/v1`; OpenAPI 3 via springdoc-openapi; unified error
envelope (`code`, `message`, `correlationId`, `fieldErrors[]`).

**Rationale**: Constitution REST discipline; React/Next client needs stable contract.

**Alternatives considered**:

- **GraphQL**: Rejected—unnecessary complexity for ticket CRUD and list filters.

## 4. List pagination and sorting

**Decision**: Spring Data `Pageable` with query parameters:

- `page` (0-based, default 0)
- `size` (default 20, max 100)
- `sort` (e.g. `createdAt,desc` | `priority,asc` | `status,asc`)

Priority sort uses custom `Sort`/`@Query` ordering mapping URGENT → HIGH → MEDIUM → LOW.

**Rationale**: Spec FR-003–FR-006; invalid page beyond range returns empty content with
correct `totalElements` (Spring default)—documented in contract.

**Alternatives considered**:

- **Cursor pagination**: Rejected for v1—offset pagination meets spec and UX for agent UI.

## 5. Frontend (out of this repo)

**Decision**: React (Vite) or Next.js in a separate client repository consuming this API;
CORS configured for known dev origins in backend plan tasks.

**Rationale**: User stack includes frontend; `TicketFlowBe` remains API-only.

**Alternatives considered**:

- **Monorepo frontend**: Deferred—current repo is backend-only; no structural change in
  this plan.

## 6. AI-assisted development

**Decision**: Cursor + GitHub Copilot; implementation follows `spec.md` → `plan.md` →
`tasks.md`; agents read constitution before codegen.

**Rationale**: User tooling; aligns with SDD workflow gates.

## 7. Cross-feature implementation order (dependency)

**Decision**: Vertical slices per spec, with foundation first:

1. Persistence + shared domain (`008`)
2. Error handling + validation (`009`, `010`)
3. Create (`001`) before list/detail/update
4. List (`002`), view (`003`), update (`004`), comments (`005`)
5. Search + filter (`006`, `007`) on same `GET /tickets` contract
6. Tests (`012`) alongside each slice; UI notifications (`011`) on client

**Rationale**: List feature spec depends on persisted tickets; cannot deliver list-only
without create + persistence.

## 8. Authentication

**Decision**: No auth in v1 implementation path; assume trusted internal network or add
Spring Security in a follow-up spec. List endpoint documents `401` placeholder for future.

**Rationale**: Specs assume authorized users without mandating auth mechanism; avoids
scope creep in this plan.

**Alternatives considered**:

- **Basic auth for dev**: Optional quickstart only, not production pattern.