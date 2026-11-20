# Implementation Plan: List Tickets

**Branch**: `002-list-tickets` | **Date**: 2026-11-20 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `specs/002-list-tickets/spec.md`  
**Stack input**: Java 21, Spring Boot, PostgreSQL/H2, REST API, React/Next.js (client),
Cursor, GitHub Copilot

## Summary

Deliver **paginated, sortable ticket listing** via `GET /api/v1/tickets` returning
**TicketSummary** projections, backed by JPA on PostgreSQL (H2 in tests). The plan
includes shared domain and API contracts needed because listing depends on persisted
tickets (create + persistence specs). Frontend consumes the same contract from a
separate React/Next app.

## Technical Context

**Language/Version**: Java 21

**Primary Dependencies**: Spring Boot 4.x Web MVC, Spring Data JPA, Bean Validation,
PostgreSQL driver, H2 (test scope), springdoc-openapi (to add in implementation)

**Storage**: PostgreSQL (`dev`/`prod` profiles); H2 memory (`test` profile only)

**Testing**: JUnit 5, Spring Boot Test, MockMvc, `@DataJpaTest` / full context for
list + pagination integration

**Target Platform**: JVM server (Linux/macOS/Windows dev); containerized deploy later

**Project Type**: Web service (backend); SPA client external

**Performance Goals**: p95 list response &lt; 500 ms with 1,000 tickets (spec SC-002:
95% under 2s—engineering headroom via indexes on `status`, `createdAt`)

**Constraints**: Constitution layered architecture; no secrets in repo; OpenAPI updated
with endpoint changes

**Scale/Scope**: Team-scale (hundreds of tickets/day); single monolith module

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

| Principle | Status | Notes |
|-----------|--------|--------|
| I. Spec-Driven Development | PASS | Plan traces to `002-list-tickets/spec.md`; tasks next |
| II. REST API Contract | PASS | `contracts/openapi.yaml` defines list + related paths |
| III. Test-Backed Changes | PASS | quickstart + MockMvc list cases in tasks |
| IV. Layered Architecture | PASS | `TicketController` → `TicketService` → `TicketRepository` |
| V. Simplicity | PASS | Offset pagination via `Pageable`; no extra services |

**Post-design re-check**: Shared OpenAPI documents full API for client alignment without
implementing all endpoints in one PR—delivery remains vertical slices per constitution V.

## Project Structure

### Documentation (this feature)

```text
specs/002-list-tickets/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/openapi.yaml
└── tasks.md              # /speckit-tasks (not yet created)
```

### Source Code (repository root)

```text
src/main/java/org/ticketflow/be/
├── TicketFlowBeApplication.java
├── controller/                     # TicketController
├── service/                        # TicketService, CommentService
├── repository/                     # TicketRepository, CommentRepository
├── domain/                         # Ticket, Comment, enums, domain exceptions
├── dto/                            # Request/response records
├── mapper/                         # TicketMapper
├── exception/                      # ApiError, GlobalExceptionHandler
└── config/                         # WebConfig, OpenApiConfig

src/main/resources/
├── application*.properties
└── db/migration/

src/test/java/org/ticketflow/be/
├── controller/                     # MockMvc ITs
└── support/IntegrationTestBase.java

# Future: TicketFlowFe/ (React Vite or Next.js) — separate repo or sibling folder
```

**Structure Decision**: Single Spring Boot Maven module at repo root (current layout).
Frontend not in this repository; API contract enables React/Next client per research.md.

## Phase 0 & 1 Artifacts

| Artifact | Path |
|----------|------|
| Research | [research.md](./research.md) |
| Data model | [data-model.md](./data-model.md) — **canonical** for all specs |
| API contract | [contracts/openapi.yaml](./contracts/openapi.yaml) — **canonical** OpenAPI |
| Validation guide | [quickstart.md](./quickstart.md) |

Other features (`001`, `003`–`012`) link here for shared model and contract; feature-local `contracts/README.md` points to this file.

## Implementation notes (this feature)

1. **Repository**: `TicketRepository extends JpaRepository<Ticket, UUID>` with
   `Page<Ticket>` for unpaged list; search/filter specs add `Specification` or `@Query`.
2. **Controller**: `GET /api/v1/tickets` maps query params to `Pageable`; validates
   `size` max 100; allowlist sort properties (`createdAt`, `priority`, `status`,
   `updatedAt`).
3. **Response**: Map `Page<Ticket>` to `TicketPage` DTO matching OpenAPI (not raw
   Spring `Page` unless wrapped for stable contract).
4. **Priority sort**: Use `@Formula` or native order in repository for custom enum order.
5. **Dependencies to add** (tasks phase): `spring-boot-starter-data-jpa`,
   `postgresql`, `spring-boot-starter-validation`, `springdoc-openapi-starter-webmvc-ui`
   (test scope unchanged for H2).

## Related specs (delivery order)

See [research.md §7](./research.md). Implement list after create + persistence;
extend same `GET` with `q` and `status` for specs 006–007.

## Complexity Tracking

> No constitution violations requiring justification.