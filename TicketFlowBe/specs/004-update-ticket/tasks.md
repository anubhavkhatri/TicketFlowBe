---
description: "Task list for Update Ticket"
---

# Tasks: Update Ticket

**Input**: `specs/004-update-ticket/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 1: Prerequisites

- [x] T001 Verify GET and POST ticket APIs exist

## Phase 2: Foundational

- [x] T002 Add `UpdateTicketRequest` in `src/main/java/org/ticketflow/be/dto/UpdateTicketRequest.java` (optional fields per data-model.md partial update)

---

## Phase 3: User Story 1 — Correct ticket information (P1) 🎯 MVP

- [x] T003 [P] [US1] Failing `shouldUpdateTitleAndDescription` in `src/test/java/org/ticketflow/be/controller/TicketControllerPatchIT.java`
- [x] T004 [US1] `TicketService.update` partial merge in `src/main/java/org/ticketflow/be/service/TicketService.java` bumping data-model.md `Ticket.updatedAt`
- [x] T005 [US1] `PATCH /api/v1/tickets/{ticketId}` in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T006 [US1] Green US1 test

---

## Phase 4: User Story 2 — Progress workflow (P2)

- [x] T007 [P] [US2] Failing status/assignee tests in `src/test/java/org/ticketflow/be/controller/TicketControllerPatchIT.java`
- [x] T008 [US2] Allow all `TicketStatus` values on update in `src/main/java/org/ticketflow/be/service/TicketService.java` per data-model.md `Ticket.status`
- [x] T009 [US2] Green US2 tests

---

## Phase 5: User Story 3 — Reject invalid updates (P3)

- [x] T010 [P] [US3] Failing invalid title/status tests in `src/test/java/org/ticketflow/be/controller/TicketControllerPatchIT.java`
- [x] T011 [US3] Validation on patch in `src/main/java/org/ticketflow/be/controller/TicketController.java` with unchanged row on failure
- [x] T012 [US3] Green US3 tests

---

## Phase 6: Polish

- [x] T013 Run `specs/004-update-ticket/quickstart.md`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T006)

## Summary

- **Total tasks**: 13
- **US1 / US2 / US3**: 4 / 3 / 3 (+ polish 1)
