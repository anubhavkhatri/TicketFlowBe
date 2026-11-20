---
description: "Task list for View Ticket"
---

# Tasks: View Ticket

**Input**: `specs/003-view-ticket/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 1: Prerequisites

- [x] T001 Verify `Ticket` entity and create API work (`specs/001-create-ticket` or `002` foundation)

## Phase 2: Foundational

- [x] T002 [P] Add `Comment` entity + `V2__create_comment_table.sql` in `src/main/resources/db/migration/` per data-model.md `Comment` if not present
- [x] T003 [P] Add `CommentRepository` in `src/main/java/org/ticketflow/be/repository/CommentRepository.java`
- [x] T004 Add `TicketDetailResponse` with nested comments in `src/main/java/org/ticketflow/be/dto/TicketDetailResponse.java`

---

## Phase 3: User Story 1 — Open ticket details (P1) 🎯 MVP

**Independent Test**: GET by id returns all ticket fields (spec US1)

- [x] T005 [P] [US1] Failing `shouldGetTicketById` in `src/test/java/org/ticketflow/be/controller/TicketControllerGetIT.java`
- [x] T006 [US1] `TicketService.getById` in `src/main/java/org/ticketflow/be/service/TicketService.java`
- [x] T007 [US1] `GET /api/v1/tickets/{ticketId}` in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T008 [US1] Green US1 test

---

## Phase 4: User Story 2 — Comment history (P2)

**Independent Test**: Comments oldest-first on detail (spec US2)

- [x] T009 [P] [US2] Failing `shouldIncludeCommentsOrdered` in `src/test/java/org/ticketflow/be/controller/TicketControllerGetIT.java`
- [x] T010 [US2] Load comments in `TicketService.getById` from `src/main/java/org/ticketflow/be/repository/CommentRepository.java` ordered by data-model.md `Comment.createdAt`
- [x] T011 [US2] Green US2 test

---

## Phase 5: User Story 3 — Missing tickets (P3)

**Independent Test**: Unknown id → 404 ApiError (spec US3)

- [x] T012 [P] [US3] Failing `shouldReturn404WhenNotFound` in `src/test/java/org/ticketflow/be/controller/TicketControllerGetIT.java`
- [x] T013 [US3] Throw not-found handled in `src/main/java/org/ticketflow/be/exception/GlobalExceptionHandler.java`
- [x] T014 [US3] Green US3 test

---

## Phase 6: Polish

- [x] T015 Run `specs/003-view-ticket/quickstart.md` validation

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T008)

## Summary

- **Total tasks**: 15
- **US1 / US2 / US3**: 4 / 3 / 3 (+ polish 1)
