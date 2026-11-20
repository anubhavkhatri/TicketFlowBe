---
description: "Task list for Filter Tickets"
---

# Tasks: Filter Tickets

**Input**: `specs/007-filter-tickets/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 3: User Story 1 — Focus on open work (P1) 🎯 MVP

- [x] T001 [P] [US1] Failing `shouldFilterOpenOnly` in `src/test/java/org/ticketflow/be/controller/TicketFilterIT.java`
- [x] T002 [US1] Status filter single value in `src/main/java/org/ticketflow/be/service/TicketService.java` on data-model.md `Ticket.status`
- [x] T003 [US1] `status` query param on `GET /api/v1/tickets` in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T004 [US1] Green US1 test

---

## Phase 4: User Story 2 — Completed work (P2)

- [x] T005 [P] [US2] Failing RESOLVED/CLOSED tests in `src/test/java/org/ticketflow/be/controller/TicketFilterIT.java`
- [x] T006 [US2] Green US2 tests

---

## Phase 5: User Story 3 — Multi-status OR (P3)

- [x] T007 [P] [US3] Failing multi-status test in `src/test/java/org/ticketflow/be/controller/TicketFilterIT.java`
- [x] T008 [US3] OR semantics for multiple `status` params in `src/main/java/org/ticketflow/be/repository/TicketRepository.java`
- [x] T009 [US3] Reject invalid status enum in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T010 [US3] Green US3 test

---

## Phase 6: Polish

- [x] T011 Combine filter with sort/page in `src/main/java/org/ticketflow/be/service/TicketService.java`
- [x] T012 Run `specs/007-filter-tickets/quickstart.md`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T004)

## Summary

- **Total tasks**: 12
- **US1 / US2 / US3**: 4 / 2 / 4 (+ polish 2)
