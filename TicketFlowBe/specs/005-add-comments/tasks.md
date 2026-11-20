---
description: "Task list for Add Comments"
---

# Tasks: Add Comments

**Input**: `specs/005-add-comments/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 2: Foundational

- [x] T001 Ensure `Comment` entity and migration in `src/main/java/org/ticketflow/be/domain/Comment.java` and `src/main/resources/db/migration/V2__create_comment_table.sql` per data-model.md `Comment`
- [x] T002 [P] Add `CreateCommentRequest` in `src/main/java/org/ticketflow/be/dto/CreateCommentRequest.java` per data-model.md `Comment.body`, `Comment.author`

---

## Phase 3: User Story 1 — Record progress (P1) 🎯 MVP

- [x] T003 [P] [US1] Failing `shouldAddComment` in `src/test/java/org/ticketflow/be/controller/TicketCommentIT.java`
- [x] T004 [US1] `CommentService.add` in `src/main/java/org/ticketflow/be/service/CommentService.java`
- [x] T005 [US1] `POST /api/v1/tickets/{ticketId}/comments` in `src/main/java/org/ticketflow/be/controller/TicketController.java`
- [x] T006 [US1] Green US1 test

---

## Phase 4: User Story 2 — Prevent empty noise (P2)

- [x] T007 [P] [US2] Failing empty body test in `src/test/java/org/ticketflow/be/controller/TicketCommentIT.java`
- [x] T008 [US2] Reject blank `Comment.body` in `src/main/java/org/ticketflow/be/service/CommentService.java`
- [x] T009 [US2] Green US2 test

---

## Phase 5: User Story 3 — Missing ticket (P3)

- [x] T010 [P] [US3] Failing 404 test in `src/test/java/org/ticketflow/be/controller/TicketCommentIT.java`
- [x] T011 [US3] Not-found before persist in `src/main/java/org/ticketflow/be/service/CommentService.java`
- [x] T012 [US3] Green US3 test

---

## Phase 6: Polish

- [x] T013 Run `specs/005-add-comments/quickstart.md`

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 3 (T006)

## Summary

- **Total tasks**: 13
- **US1 / US2 / US3**: 4 / 3 / 3 (+ polish 1)
