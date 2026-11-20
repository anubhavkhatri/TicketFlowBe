---
description: "Task list for Automated Testing"
---

# Tasks: Automated Testing

**Input**: `specs/012-automated-testing/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 2: Foundational

- [x] T001 Ensure `@SpringBootTest` + MockMvc pattern in `src/test/java/org/ticketflow/be/controller/TicketControllerCreateIT.java` as template
- [x] T002 [P] Add `src/test/resources/application-test.properties` mirror if missing

---

## Phase 3: User Story 1 — Guard lifecycle (P1) 🎯 MVP

- [x] T003 [P] [US1] Add or verify create/list/get/patch IT classes under `src/test/java/org/ticketflow/be/controller/`
- [x] T004 [US1] Map each to spec `001`–`007` acceptance scenarios in test method names
- [x] T005 [US1] Document mapping table in `specs/012-automated-testing/quickstart.md`

---

## Phase 4: User Story 2 — Comments (P2)

- [x] T006 [P] [US2] Ensure `TicketCommentIT` covers add + view in `src/test/java/org/ticketflow/be/controller/TicketCommentIT.java`
- [x] T007 [US2] Green comment path in `./mvnw test`

---

## Phase 5: User Story 3 — Errors (P3)

- [x] T008 [P] [US3] Extend `ApiErrorIT` and `ValidationIT` in `src/test/java/org/ticketflow/be/controller/`
- [x] T009 [US3] Assert error categories per openapi in `src/test/java/org/ticketflow/be/controller/ApiErrorIT.java`

---

## Phase 6: Polish

- [x] T010 Add CI note to run `./mvnw test` on every PR in `README.md` or `.github/workflows/ci.yml` if present
- [x] T011 Full suite green: `./mvnw test`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T005)

## Summary

- **Total tasks**: 11
- **US1 / US2 / US3**: 3 / 2 / 2 (+ polish 2)
