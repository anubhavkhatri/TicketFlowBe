---
description: "Task list for Ticket and Comment Persistence"
---

# Tasks: Ticket and Comment Persistence

**Input**: `specs/008-ticket-persistence/` (plan.md, spec.md, research.md, data-model.md → [specs/002-list-tickets/data-model.md](../002-list-tickets/data-model.md))
**Prerequisites**: Complete **`008-ticket-persistence`** (or Phase 1–2 of `002-list-tickets/tasks.md`) before feature US phases unless noted.
**Tests**: MockMvc + `@ActiveProfiles("test")` per constitution III.

## Format: `[ID] [P?] [Story] Description`


## Phase 1: Setup

- [x] T001 Add `spring-boot-starter-data-jpa`, PostgreSQL runtime, Flyway in `pom.xml`
- [x] T002 [P] Configure `application-dev.properties` for PostgreSQL in `src/main/resources/application-dev.properties`
- [x] T003 [P] Configure H2 test datasource in `src/main/resources/application-test.properties`

---

## Phase 2: Foundational 🎯 MVP core

- [x] T004 Add `V1__create_ticket_table.sql` in `src/main/resources/db/migration/` per data-model.md `Ticket` fields
- [x] T005 [P] Add `Ticket.java` entity in `src/main/java/org/ticketflow/be/domain/Ticket.java`
- [x] T006 [P] Add enums in `src/main/java/org/ticketflow/be/domain/TicketPriority.java` and `TicketStatus.java`
- [x] T007 Add `TicketRepository` in `src/main/java/org/ticketflow/be/repository/TicketRepository.java`
- [x] T008 [P] Integration test `TicketRepositoryIT` in `src/test/java/org/ticketflow/be/repository/TicketRepositoryIT.java` with `@ActiveProfiles("test")`

---

## Phase 3: User Story 1 — Durable tickets (P1)

- [x] T009 [P] [US1] Test restart persistence scenario in `src/test/java/org/ticketflow/be/repository/TicketRepositoryIT.java`
- [x] T010 [US1] Set `spring.jpa.hibernate.ddl-auto=validate` for prod profile in `src/main/resources/application.properties`

---

## Phase 4: User Story 2 — Comments tied to tickets (P2)

- [x] T011 [P] [US2] Add `V2__create_comment_table.sql` FK to ticket in `src/main/resources/db/migration/`
- [x] T012 [US2] Add `Comment.java` in `src/main/java/org/ticketflow/be/domain/Comment.java` per data-model.md `Comment.ticketId`
- [x] T013 [P] [US2] `CommentRepositoryIT` in `src/test/java/org/ticketflow/be/repository/CommentRepositoryIT.java`

---

## Phase 5: User Story 3 — Operational store (P3)

- [x] T014 [US3] Add NOT NULL and check constraints on enums in `V1__create_ticket_table.sql`
- [x] T015 [P] [US3] Test atomic failure on invalid enum in `src/test/java/org/ticketflow/be/repository/TicketRepositoryIT.java`

---

## Phase 6: Polish

- [x] T016 Document profile matrix in `specs/008-ticket-persistence/quickstart.md`
- [x] T017 Run `./mvnw test` with test profile

---

## Dependencies & Execution Order

- Phase 1 → Phase 2 (if any) → US1 → US2 → US3 → Polish
- **MVP**: Phase 2 (T008)

## Summary

- **Total tasks**: 17
- **US1 / US2 / US3**: 2 / 3 / 3 (+ polish 2)
