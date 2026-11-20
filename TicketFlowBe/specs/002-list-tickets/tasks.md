---
description: "Task list for List Tickets feature (002-list-tickets)"
---

# Tasks: List Tickets

**Input**: Design documents from `specs/002-list-tickets/`  
**Prerequisites**: [plan.md](./plan.md), [spec.md](./spec.md), [research.md](./research.md), [data-model.md](./data-model.md), [contracts/openapi.yaml](./contracts/openapi.yaml)

**Tests**: Included per constitution III (MockMvc + H2 integration).

**Organization**: Phases follow user stories US1 (browse), US2 (sort), US3 (stable pages). Foundational work includes persistence and minimal **create** (`POST /api/v1/tickets`) so list has seed data (per research.md §7 / spec `001` dependency).

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies on incomplete tasks)
- **[Story]**: US1, US2, US3 for user-story phases only

---

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Maven dependencies and package layout from [plan.md](./plan.md)

- [x] T001 Add JPA, Validation, PostgreSQL, springdoc dependencies in `pom.xml`
- [x] T002 [P] Create package dirs `config`, `domain`, `repository`, `service`, `web`, `web/dto`, `web/error` under `src/main/java/org/ticketflow/be/`
- [x] T003 [P] Add `application-dev.properties` and `application-test.properties` under `src/main/resources/` with H2 test profile per research.md
- [x] T004 [P] Add `OpenApiConfig` in `src/main/java/org/ticketflow/be/config/OpenApiConfig.java`
- [x] T005 [P] Point springdoc at `specs/002-list-tickets/contracts/openapi.yaml` or generate from controllers in `src/main/resources/application.properties`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Ticket persistence, error envelope, and create API to seed list data  
**⚠️ CRITICAL**: No user story list work until this phase completes

- [x] T006 Add Flyway and `V1__create_ticket_table.sql` in `src/main/resources/db/migration/` for Ticket columns per data-model.md `Ticket` (id, title, description, priority, status, assignee, createdAt, updatedAt)
- [x] T007 [P] Add `TicketPriority` enum in `src/main/java/org/ticketflow/be/domain/TicketPriority.java` per data-model.md `Ticket.priority`
- [x] T008 [P] Add `TicketStatus` enum in `src/main/java/org/ticketflow/be/domain/TicketStatus.java` per data-model.md `Ticket.status`
- [x] T009 Create `Ticket` JPA entity in `src/main/java/org/ticketflow/be/domain/Ticket.java` mapping data-model.md `Ticket.id`, `Ticket.title`, `Ticket.description`, `Ticket.priority`, `Ticket.status`, `Ticket.assignee`, `Ticket.createdAt`, `Ticket.updatedAt`
- [x] T010 Add DB indexes for list in `V1__create_ticket_table.sql` per data-model.md indexes (`status`, `createdAt`)
- [x] T011 Create `TicketRepository` in `src/main/java/org/ticketflow/be/repository/TicketRepository.java` extending `JpaRepository<Ticket, UUID>`
- [x] T012 [P] Add `ApiError` and `FieldError` records in `src/main/java/org/ticketflow/be/exception/ApiError.java` matching `contracts/openapi.yaml`
- [x] T013 Add `GlobalExceptionHandler` in `src/main/java/org/ticketflow/be/exception/GlobalExceptionHandler.java` for validation and not-found (constitution II / spec 010 alignment)
- [x] T014 [P] Add `PageableConfig` or `WebMvcConfig` in `src/main/java/org/ticketflow/be/config/WebConfig.java` with `size` default 20 and max 100 per data-model.md validation matrix
- [x] T015 Add `CreateTicketRequest`, `TicketDetailResponse` DTOs in `src/main/java/org/ticketflow/be/dto/` citing data-model.md `Ticket.title` (required, max 200), `Ticket.description` (max 10000), defaults for `Ticket.priority`/`Ticket.status`
- [x] T016 Implement `TicketService.create` in `src/main/java/org/ticketflow/be/service/TicketService.java` with validation and persist per spec `001-create-ticket` FR-002–FR-009
- [x] T017 Implement `POST /api/v1/tickets` in `src/main/java/org/ticketflow/be/controller/TicketController.java` returning 201 + `TicketDetailResponse`
- [x] T018 [P] Add `TicketControllerCreateIT` MockMvc test in `src/test/java/org/ticketflow/be/controller/TicketControllerCreateIT.java` using `@ActiveProfiles("test")`

**Checkpoint**: Foundation ready — tickets can be created and persisted; list implementation can begin

---

## Phase 3: User Story 1 — Browse the ticket queue (Priority: P1) 🎯 MVP

**Goal**: Paginated `GET /api/v1/tickets` returning ticket summaries and page metadata (spec FR-001–FR-004, FR-007)

**Independent Test**: Seed >20 tickets via POST; `GET ?page=0&size=20` returns 20 summaries + `totalElements` / `totalPages`; second page when more exist (spec US1).

### Tests for User Story 1

- [x] T019 [P] [US1] Add failing `TicketControllerListIT.shouldReturnFirstPageWithMetadata` in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java` per quickstart.md §3
- [x] T020 [P] [US1] Add failing `TicketControllerListIT.shouldReturnEmptyListWhenNoTickets` in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`

### Implementation for User Story 1

- [x] T021 [P] [US1] Add `TicketSummaryResponse` and `TicketPageResponse` in `src/main/java/org/ticketflow/be/dto/TicketSummaryResponse.java` and `TicketPageResponse.java` with fields per data-model.md TicketSummary (`Ticket.id`, `Ticket.title`, `Ticket.priority`, `Ticket.status`, `Ticket.assignee`, `Ticket.createdAt`, `Ticket.updatedAt`) — exclude description
- [x] T022 [US1] Implement `TicketService.list(Pageable)` in `src/main/java/org/ticketflow/be/service/TicketService.java` returning domain page
- [x] T023 [US1] Implement `GET /api/v1/tickets` in `src/main/java/org/ticketflow/be/controller/TicketController.java` mapping to `TicketPageResponse` per `contracts/openapi.yaml` `listTickets`
- [x] T024 [US1] Map entity to summary DTO in `src/main/java/org/ticketflow/be/mapper/TicketMapper.java` (new file)
- [x] T025 [US1] Green US1 tests in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`

**Checkpoint**: User Story 1 independently testable — browse first page with metadata

---

## Phase 4: User Story 2 — Order work by urgency or age (Priority: P2)

**Goal**: Sort by `createdAt`, `priority`, `status` asc/desc (spec FR-005–FR-006)

**Independent Test**: Mixed priorities; `sort=priority,desc` orders URGENT before LOW per data-model.md priority ordering

### Tests for User Story 2

- [x] T026 [P] [US2] Add failing `TicketControllerListIT.shouldSortByCreatedAt` in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`
- [x] T027 [P] [US2] Add failing `TicketControllerListIT.shouldSortByPriorityDesc` in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`

### Implementation for User Story 2

- [x] T028 [US2] Add custom priority sort in `src/main/java/org/ticketflow/be/repository/TicketRepository.java` or `TicketService.java` per data-model.md priority ordering (URGENT > HIGH > MEDIUM > LOW)
- [x] T029 [US2] Allowlist sort properties (`createdAt`, `priority`, `status`, `updatedAt`) in `src/main/java/org/ticketflow/be/controller/TicketController.java` or `PageableConfig` with default `createdAt,desc` per spec assumptions
- [x] T030 [US2] Apply secondary sort `createdAt,desc` when primary is not `createdAt` per spec FR-006 in `src/main/java/org/ticketflow/be/service/TicketService.java`
- [x] T031 [US2] Green US2 sort tests in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`

**Checkpoint**: User Stories 1 and 2 — browse + sort independently verified

---

## Phase 5: User Story 3 — Stable navigation across pages (Priority: P3)

**Goal**: Consistent pages; invalid sort rejected; out-of-range page behavior per spec edge cases

**Independent Test**: Traverse pages 0 and 1 with same sort; no duplicate or missing ids (spec US3)

### Tests for User Story 3

- [x] T032 [P] [US3] Add failing `TicketControllerListIT.shouldNotDuplicateTicketsAcrossPages` in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`
- [x] T033 [P] [US3] Add failing `TicketControllerListIT.shouldRejectInvalidSortField` in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`
- [x] T034 [P] [US3] Add failing `TicketControllerListIT.shouldRejectSizeAboveMax` citing data-model.md list query `size` (1–100) in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`

### Implementation for User Story 3

- [x] T035 [US3] Validate `page` ≥ 0 and `size` bounds in `src/main/java/org/ticketflow/be/controller/TicketController.java` with `ApiError` field errors via `GlobalExceptionHandler`
- [x] T036 [US3] Return empty `content` with correct `totalElements` when `page` beyond last (Spring default) and document in `TicketPageResponse` in `src/main/java/org/ticketflow/be/dto/TicketPageResponse.java`
- [x] T037 [US3] Reject disallowed `sort` property with 400 and allowed-field list in `src/main/java/org/ticketflow/be/exception/GlobalExceptionHandler.java`
- [x] T038 [US3] Green US3 tests in `src/test/java/org/ticketflow/be/controller/TicketControllerListIT.java`

**Checkpoint**: All list user stories complete

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Contract sync, docs, full suite

- [x] T039 [P] Sync `specs/002-list-tickets/contracts/openapi.yaml` with implemented `TicketPageResponse` if fields diverged
- [x] T040 Run `./mvnw test` and fix failures across `src/test/java/org/ticketflow/be/`
- [x] T041 Execute manual checks in `specs/002-list-tickets/quickstart.md` §3 and note results in `specs/002-list-tickets/quickstart.md`
- [x] T042 [P] Add structured logging for list failures in `src/main/java/org/ticketflow/be/controller/TicketController.java` with correlation id per constitution observability

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)** → **Foundational (Phase 2)** → **US1 → US2 → US3** → **Polish**
- Foundational **blocks** all user stories (needs `Ticket` entity + create API)

### User Story Dependencies

- **US1 (P1)**: After Phase 2; no dependency on US2/US3
- **US2 (P2)**: After US1 `GET` exists (extends same endpoint)
- **US3 (P3)**: After US1; validation tasks can overlap US2 end

### Parallel Opportunities

- Phase 1: T002–T005 parallel after T001
- Phase 2: T007–T008, T012, T014, T018 parallel where noted
- US1 tests T019–T020 parallel; DTO T021 parallel with tests before impl
- US2/US3 test tasks marked [P] parallel before implementation tasks

### Parallel Example: User Story 1

```text
T019 + T020 (failing list tests)
T021 (DTOs)
→ T022–T024 (service, controller, mapper)
→ T025 (green tests)
```

---

## Implementation Strategy

### MVP First (User Story 1 only)

1. Phase 1 + Phase 2 (including create for seed data)
2. Phase 3 (US1)
3. **Stop** — validate with quickstart.md §3 first two curls
4. Demo paginated list to stakeholders

### Incremental Delivery

1. Foundation + create → data exists
2. US1 browse → MVP list
3. US2 sort → actionable queue
4. US3 validation/stability → production-ready list edge cases
5. Polish → contract + quickstart sign-off

---

## Notes

- **Total tasks**: 42 (Setup 5, Foundational 13, US1 7, US2 6, US3 7, Polish 4)
- **MVP scope**: Phases 1–3 (through T025)
- **Field citations**: All constrained Ticket fields referenced via data-model.md in T009, T015, T021, T034
- Assign tasks to Cursor/Copilot with links to `specs/002-list-tickets/plan.md` and this file