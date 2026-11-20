---
description: "Task list for Search Tickets"
---

# Tasks: Search Tickets

**Input**: `specs/006-search-tickets/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 2: Foundational

- [x] T001 Extend `TicketRepository` with search method in `src/main/java/org/ticketflow/be/repository/TicketRepository.java` (Specification or `@Query`)

---

## Phase 3: User Story 1 — Find by id (P1) 🎯 MVP

- [x] T002 [P] [US1] Failing `shouldFindByExactId` in `src/test/java/org/ticketflow/be/controller/TicketSearchIT.java`
- [x] T003 [US1] Exact UUID branch for `q` in `src/main/java/org/ticketflow/be/service/TicketService.java`
- [x] T004 [US1] Wire `q` param on `GET /api/v1/tickets` in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T005 [US1] Green US1 test

---

## Phase 4: User Story 2 — Keyword search (P2)

- [x] T006 [P] [US2] Failing title/description tests in `src/test/java/org/ticketflow/be/controller/TicketSearchIT.java`
- [x] T007 [US2] Case-insensitive LIKE on data-model.md `Ticket.title` and `Ticket.description` in `src/main/java/org/ticketflow/be/repository/TicketRepository.java`
- [x] T008 [US2] Green US2 tests

---

## Phase 5: User Story 3 — Empty results (P3)

- [x] T009 [P] [US3] Failing `shouldReturnEmptyWhenNoMatch` in `src/test/java/org/ticketflow/be/controller/TicketSearchIT.java`
- [x] T010 [US3] Reject empty `q` after trim in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T011 [US3] Green US3 test

---

## Phase 6: Polish

- [x] T012 Compose search with pagination in `src/main/java/org/ticketflow/be/service/TicketService.java`
- [x] T013 Run `specs/006-search-tickets/quickstart.md`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T005)

## Summary

- **Total tasks**: 13
- **US1 / US2 / US3**: 4 / 3 / 3 (+ polish 2)
