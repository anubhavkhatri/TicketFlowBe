---
description: "Task list for Backend Request Validation"
---

# Tasks: Backend Request Validation

**Input**: `specs/009-request-validation/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 2: Foundational

- [x] T001 Add `spring-boot-starter-validation` if missing in `pom.xml`
- [x] T002 [P] Add constraint annotations on all DTOs under `src/main/java/org/ticketflow/be/dto/` citing data-model.md field rules

---

## Phase 3: User Story 1 — Block bad input (P1) 🎯 MVP

- [x] T003 [P] [US1] Parametrized invalid POST tests in `src/test/java/org/ticketflow/be/controller/ValidationIT.java`
- [x] T004 [US1] Ensure `@Valid` on create/update/comment in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T005 [US1] Green US1 tests

---

## Phase 4: User Story 2 — Consistent rules (P2)

- [x] T006 [P] [US2] Same priority invalid payload on create vs PATCH in `src/test/java/org/ticketflow/be/controller/ValidationIT.java`
- [x] T007 [US2] Shared messages via `GlobalExceptionHandler` in `src/main/java/org/ticketflow/be/exception/GlobalExceptionHandler.java`
- [x] T008 [US2] Green US2 tests

---

## Phase 5: User Story 3 — Mandatory vs optional (P3)

- [x] T009 [P] [US3] PATCH omit title test in `src/test/java/org/ticketflow/be/controller/ValidationIT.java`
- [x] T010 [US3] Document partial-update validation in `src/main/java/org/ticketflow/be/service/TicketService.java`
- [x] T011 [US3] Green US3 test

---

## Phase 6: Polish

- [x] T012 Add list param validation (`size` max 100) in `src/main/java/org/ticketflow/be/config/WebConfig.java`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T005)

## Summary

- **Total tasks**: 12
- **US1 / US2 / US3**: 3 / 3 / 3 (+ polish 1)
