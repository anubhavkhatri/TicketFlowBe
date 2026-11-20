---
description: "Task list for API Error Handling"
---

# Tasks: API Error Handling

**Input**: `specs/010-api-error-handling/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 2: Foundational

- [x] T001 [P] Add `ApiError` + `FieldError` in `src/main/java/org/ticketflow/be/exception/ApiError.java` matching openapi `ApiError`
- [x] T002 Add `TicketNotFoundException` in `src/main/java/org/ticketflow/be/domain/TicketNotFoundException.java`

---

## Phase 3: User Story 1 — Predictable failures (P1) 🎯 MVP

- [x] T003 [P] [US1] Failing validation/not-found shape tests in `src/test/java/org/ticketflow/be/controller/ApiErrorIT.java`
- [x] T004 [US1] `GlobalExceptionHandler` in `src/main/java/org/ticketflow/be/exception/GlobalExceptionHandler.java` for `MethodArgumentNotValidException` and not-found
- [x] T005 [US1] Green US1 tests

---

## Phase 4: User Story 2 — No leakage (P2)

- [x] T006 [P] [US2] Assert no stack trace in body in `src/test/java/org/ticketflow/be/controller/ApiErrorIT.java`
- [x] T007 [US2] Generic 500 body + correlation id in `src/main/java/org/ticketflow/be/exception/GlobalExceptionHandler.java`
- [x] T008 [US2] Green US2 test

---

## Phase 5: User Story 3 — Central policy (P3)

- [x] T009 [P] [US3] `@RestControllerAdvice` coverage audit test in `src/test/java/org/ticketflow/be/controller/ApiErrorIT.java`
- [x] T010 [US3] Map all ticket controllers under single advice in `src/main/java/org/ticketflow/be/exception/GlobalExceptionHandler.java`

---

## Phase 6: Polish

- [x] T011 Add structured error logging in `src/main/java/org/ticketflow/be/exception/GlobalExceptionHandler.java`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T005)

## Summary

- **Total tasks**: 11
- **US1 / US2 / US3**: 3 / 3 / 2 (+ polish 1)
