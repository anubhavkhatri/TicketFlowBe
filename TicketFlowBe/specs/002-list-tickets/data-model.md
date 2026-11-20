# Data Model: TicketFlow

**Feature anchor**: `002-list-tickets` (list uses **Ticket** summary; full model shared across specs)

## Entity: Ticket

| Field | Type | Required | Rules |
|-------|------|----------|--------|
| `id` | UUID (or BIGINT surrogate) | yes | Server-generated, immutable |
| `title` | string | yes | Trim; max 200; non-empty |
| `description` | text | no | Max 10,000; default empty |
| `priority` | enum | yes | `LOW`, `MEDIUM`, `HIGH`, `URGENT`; default `MEDIUM` on create if omitted |
| `status` | enum | yes | `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`, `CANCELLED`; create allows OPEN/IN_PROGRESS only |
| `assignee` | string | no | Trim; null = unassigned; max 320 if email-like |
| `createdAt` | instant | yes | Set on insert |
| `updatedAt` | instant | yes | Set on insert/update |

**Indexes** (for list, search, filter):

- `idx_ticket_status` on `status`
- `idx_ticket_created_at` on `createdAt`
- Full-text or `LIKE` on title/description for search (v1: case-insensitive `LIKE` per spec `006`)

**State transitions** (enforced on update):

- `OPEN` → `IN_PROGRESS`, `CANCELLED`
- `IN_PROGRESS` → `RESOLVED`, `CANCELLED`
- `RESOLVED` → `CLOSED`
- `CLOSED`, `CANCELLED` → terminal (no outbound transitions)

## Entity: Comment

| Field | Type | Required | Rules |
|-------|------|----------|--------|
| `id` | UUID / BIGINT | yes | Server-generated |
| `ticketId` | FK → Ticket | yes | Must exist |
| `body` | text | yes | Trim; non-empty; max 5,000 |
| `author` | string | yes | Trim; non-empty |
| `createdAt` | instant | yes | Set on insert |

**Relationship**: Many comments → one ticket; cascade delete on ticket if delete added later
(out of scope v1).

## DTO projections

### TicketSummary (list, search, filter)

`id`, `title`, `priority`, `status`, `assignee`, `createdAt`, `updatedAt` — excludes
`description` and comments (spec `002` FR-002).

### TicketDetail (view)

All ticket fields + `comments[]` ordered by `createdAt` ascending.

## Validation matrix (centralized)

| Operation | Mandatory fields |
|-----------|------------------|
| Create | `title`; `priority`/`status` default if omitted |
| Update | At least one mutable field; same field rules as create when present |
| Add comment | `body`, `author` |
| List query | `page` ≥ 0, `1 ≤ size ≤ 100`, sort field in allowlist |

## Enumeration ordering (sort)

**Priority** (desc = most urgent first): URGENT(4) > HIGH(3) > MEDIUM(2) > LOW(1).

**Status** (lexicographic unless custom order in UI): use enum ordinal only for sort
requests on `status`; document in API.