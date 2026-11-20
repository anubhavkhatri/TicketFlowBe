---
description: "Task list for UI Notifications"
---

# Tasks: UI Notifications

**Input**: `specs/011-ui-notifications/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 1: Setup (frontend)

- [ ] T001 Initialize React Vite app at `frontend/` (or sibling `TicketFlowFe/`) per `specs/011-ui-notifications/plan.md`
- [ ] T002 [P] Add API client `frontend/src/lib/ticketApi.ts` pointing to `http://localhost:8080/api/v1`

---

## Phase 3: User Story 1 — Confirm success (P1) 🎯 MVP

- [ ] T003 [P] [US1] Add toast provider in `frontend/src/components/ToastProvider.tsx`
- [ ] T004 [US1] Show success toast on create/update in `frontend/src/pages/TicketForm.tsx`
- [ ] T005 [US1] Manual check per `specs/011-ui-notifications/quickstart.md`

---

## Phase 4: User Story 2 — Validation errors (P2)

- [ ] T006 [P] [US2] Parse `fieldErrors` from ApiError in `frontend/src/lib/parseApiError.ts`
- [ ] T007 [US2] Map field errors to form in `frontend/src/pages/TicketForm.tsx`
- [ ] T008 [US2] Manual validation UX test

---

## Phase 5: User Story 3 — API failures (P3)

- [ ] T009 [P] [US3] Network/5xx toast in `frontend/src/lib/ticketApi.ts`
- [ ] T010 [US3] Distinguish timeout vs validation in `frontend/src/components/ToastProvider.tsx`

---

## Phase 6: Polish

- [ ] T011 [P] Debounce double-submit in `frontend/src/pages/TicketForm.tsx`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T005)

## Summary

- **Total tasks**: 11
- **US1 / US2 / US3**: 3 / 3 / 2 (+ polish 1)
