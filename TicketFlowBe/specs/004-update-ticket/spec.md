# Feature Specification: Update Ticket

**Feature Branch**: `004-update-ticket`

**Created**: 2026-11-20

**Status**: Draft

**Input**: User description: "Update Ticket: Update ticket title, description, priority, status, and assignee."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Correct ticket information (Priority: P1)

An agent fixes title or description after learning new facts from the customer.

**Why this priority**: Inaccurate records block resolution and reporting.

**Independent Test**: Update title and description; view ticket; confirm changes
and updated-at movement.

**Acceptance Scenarios**:

1. **Given** an existing ticket, **When** the agent submits valid new title and
   description, **Then** stored values change and confirmation reflects updates.
2. **Given** an update succeeds, **When** the ticket is viewed, **Then**
   unchanged fields retain prior values.

---

### User Story 2 - Progress workflow (Priority: P2)

An agent changes priority, status, or assignee as work advances.

**Independent Test**: Move ticket from OPEN to IN_PROGRESS to RESOLVED; change
assignee; verify each transition.

**Acceptance Scenarios**:

1. **Given** OPEN ticket, **When** status set to IN_PROGRESS, **Then** change
  persists and is visible on view.
2. **Given** valid assignee text, **When** assignee updated, **Then** new
   assignee shows; clearing assignee sets unassigned.

---

### User Story 3 - Reject invalid updates (Priority: P3)

An agent attempts an invalid status jump or empty title.

**Independent Test**: Submit invalid payload; ticket unchanged; field errors
returned.

**Acceptance Scenarios**:

1. **Given** a ticket, **When** title cleared to whitespace only, **Then**
   update rejected and title unchanged.
2. **Given** invalid priority or status value, **When** update attempted, **Then**
   rejection lists allowed values.

---

### Edge Cases

- Partial updates: only supplied fields change (patch semantics) per Assumptions.
- Terminal status RESOLVED or CLOSED: allowed on update; reopening CLOSED to
  OPEN allowed in v1 unless Assumption changed in plan.
- Concurrent updates: last successful write wins; no optimistic locking in v1.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST allow authorized users to update title, description,
  priority, status, and assignee on existing tickets.
- **FR-002**: System MUST enforce same validation rules as create for each field
  when that field is included in an update.
- **FR-003**: System MUST allow status values: OPEN, IN_PROGRESS, RESOLVED,
  CLOSED on update.
- **FR-004**: System MUST update an updated-at timestamp on successful change.
- **FR-005**: System MUST support partial updates without requiring all fields.
- **FR-006**: System MUST return not-found when identifier does not exist.
- **FR-007**: System MUST reject updates that violate validation with structured
  field errors.

### Key Entities

- **Ticket**: Mutable record; same attributes as create feature with lifecycle
  statuses.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 95% of agents complete a routine field correction in under 45
  seconds in moderated tests.
- **SC-002**: 100% of invalid update attempts in acceptance tests leave stored
  data unchanged.
- **SC-003**: Status transitions to RESOLVED and CLOSED are reflected in filter
  and list views within one refresh.

## Assumptions

- Partial update (patch) semantics: omitted fields remain unchanged.
- Priority enum unchanged: LOW, MEDIUM, HIGH, URGENT.
- Comments are not modified by this feature.
- Aligns with `001-create-ticket` field limits (title 200, description 10,000).