# Feature Specification: Add Comments

**Feature Branch**: `005-add-comments`

**Created**: 2026-11-20

**Status**: Draft

**Input**: User description: "Add Comments: Allow users to add comments to existing tickets."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Record progress on a ticket (Priority: P1)

An agent adds a comment summarizing a customer call so teammates see what
happened.

**Why this priority**: Collaboration is core to support workflows.

**Independent Test**: Post comment on existing ticket; view ticket; see new
comment at end of history.

**Acceptance Scenarios**:

1. **Given** an existing ticket, **When** the agent submits non-empty comment
   text and author identity, **Then** comment is stored with timestamp and
   appears in history.
2. **Given** a new comment, **When** detail is viewed, **Then** ticket fields
   are unchanged except updated-at if applicable.

---

### User Story 2 - Prevent empty noise (Priority: P2)

An agent accidentally submits a blank comment.

**Independent Test**: Submit whitespace-only body; no comment created; clear
error.

**Acceptance Scenarios**:

1. **Given** a ticket, **When** comment body is empty or whitespace only,
   **Then** system rejects and does not append to history.

---

### User Story 3 - Comment on missing ticket (Priority: P3)

An agent comments using a bad ticket reference.

**Acceptance Scenarios**:

1. **Given** unknown ticket id, **When** comment submitted, **Then** not-found
   with no orphan comment stored.

---

### Edge Cases

- Maximum comment length exceeded: reject with limit message (see Assumptions).
- Rapid duplicate submissions: each creates distinct comment (no dedup).
- CLOSED ticket: comments still allowed in v1 (reopen discussion).

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST allow authorized users to add a comment to an
  existing ticket in one action.
- **FR-002**: System MUST require non-empty comment body after trim.
- **FR-003**: System MUST record author identifier and created-at on each
  comment.
- **FR-004**: System MUST assign each comment a unique identifier.
- **FR-005**: System MUST reject comment creation for non-existent tickets.
- **FR-006**: System MUST persist comments with the same durability as tickets.
- **FR-007**: System MUST apply server-side validation regardless of client.

### Key Entities

- **Comment**: Body, author, created-at, parent ticket reference.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 95% of agents add a comment in under 30 seconds in moderated tests.
- **SC-002**: 100% of stored comments remain visible after application restart
  in acceptance tests.
- **SC-003**: Zero orphan comments in acceptance tests when ticket id invalid.

## Assumptions

- Author is supplied by client or inferred from session in plan; v1 accepts
  explicit author text if auth not yet present.
- Comment body limit: 5,000 characters.
- Editing or deleting comments is out of scope for v1.