---
description: "Task list for Create Support Ticket"
---

# Tasks: Create Support Ticket

**Input**: `specs/001-create-ticket/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 1: Prerequisites

- [x] T001 Verify JPA and validation dependencies exist in `pom.xml` (add if missing per `specs/001-create-ticket/plan.md`)

## Phase 2: Foundational (create slice)

**Purpose**: DTOs and service method for create only (entity from `008`)

- [x] T002 [P] Add `CreateTicketRequest` in `src/main/java/org/ticketflow/be/dto/CreateTicketRequest.java` with constraints per data-model.md `Ticket.title`, `Ticket.description`, `Ticket.priority`, `Ticket.status`, `Ticket.assignee`
- [x] T003 [P] Add `TicketDetailResponse` in `src/main/java/org/ticketflow/be/dto/TicketDetailResponse.java`
- [x] T004 Implement `TicketService.create` in `src/main/java/org/ticketflow/be/service/TicketService.java` (defaults MEDIUM/OPEN per spec assumptions; create status OPEN|IN_PROGRESS only)

---

## Phase 3: User Story 1 — Log a new support ticket (P1) 🎯 MVP

**Independent Test**: POST valid body → 201 + id and echoed fields (spec US1)

- [x] T005 [P] [US1] Failing test `shouldCreateTicket` in `src/test/java/org/ticketflow/be/controller/TicketControllerCreateIT.java`
- [x] T006 [US1] `POST /api/v1/tickets` in `src/main/java/org/ticketflow/be/controller/TicketController.java` per `contracts/openapi.yaml` `createTicket`
- [x] T007 [US1] Green `TicketControllerCreateIT.shouldCreateTicket`

---

## Phase 4: User Story 2 — Validation problems (P2)

**Independent Test**: Missing title → 400 fieldErrors, no row (spec US2)

- [x] T008 [P] [US2] Failing tests for title/priority/status in `src/test/java/org/ticketflow/be/controller/TicketControllerCreateIT.java`
- [x] T009 [US2] Wire `@Valid` and enum checks on `CreateTicketRequest` in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T010 [US2] Green US2 tests in `src/test/java/org/ticketflow/be/controller/TicketControllerCreateIT.java`

---

## Phase 5: User Story 3 — Optional assignment (P3)

**Independent Test**: Omit assignee → persisted unassigned (spec US3)

- [x] T011 [P] [US3] Failing `shouldCreateUnassigned` in `src/test/java/org/ticketflow/be/controller/TicketControllerCreateIT.java`
- [x] T012 [US3] Handle null assignee in `src/main/java/org/ticketflow/be/service/TicketService.java` per data-model.md `Ticket.assignee`
- [x] T013 [US3] Green US3 test

---

## Phase 6: Polish

- [x] T014 [P] Update `specs/002-list-tickets/contracts/openapi.yaml` if create response shape changed
- [x] T015 Run `specs/001-create-ticket/quickstart.md` curls and document in `specs/001-create-ticket/quickstart.md`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phases 1–3 (T007)

## Summary

- **Total tasks**: 15
- **US1 / US2 / US3**: 3 / 3 / 3 (+ polish 2)
