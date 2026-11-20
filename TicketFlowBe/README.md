# TicketFlowBe

REST API backend for **TicketFlow** — a support ticket system (create, list, view, update, comments, search, and filter).

Built with **Java 21** and **Spring Boot**, using **Spec-Driven Development (SDD)** via [Spec Kit](https://github.com/github/spec-kit) artifacts under `specs/`.

## Features

| Capability | HTTP |
|------------|------|
| Create ticket | `POST /api/v1/tickets` |
| List (pagination, sort) | `GET /api/v1/tickets` |
| Search | `GET /api/v1/tickets?q=` |
| Filter by status | `GET /api/v1/tickets?status=OPEN&status=IN_PROGRESS` |
| View ticket + comments | `GET /api/v1/tickets/{id}` |
| Update ticket | `PATCH /api/v1/tickets/{id}` |
| Add comment | `POST /api/v1/tickets/{id}/comments` |

OpenAPI contract (source of truth for reviews): [`specs/002-list-tickets/contracts/openapi.yaml`](specs/002-list-tickets/contracts/openapi.yaml).

## Tech stack

- Java 21, Maven
- Spring Boot Web MVC, Spring Data JPA, Bean Validation, Flyway
- PostgreSQL (dev/prod), H2 (tests only)
- springdoc OpenAPI
- JUnit 5, MockMvc

A separate **React / Next.js** client is expected to consume this API (not in this repo).

## Prerequisites

- **JDK 21**
- **Maven** (or use `./mvnw`)
- **PostgreSQL 15+** for local `dev` profile (optional; tests use H2)

## Quick start

### Run tests (H2, no PostgreSQL required)

```bash
./mvnw test
```

Uses profile `test` via `src/main/resources/application-test.properties`.

### Run locally with PostgreSQL

1. Start PostgreSQL and create database `ticketflow` (adjust credentials in `application-dev.properties`).

```bash
docker run -d --name ticketflow-pg \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=ticketflow \
  -p 5432:5432 postgres:16
```

2. Start the application:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

3. API base URL: `http://localhost:8080/api/v1`

Example:

```bash
curl -s -X POST http://localhost:8080/api/v1/tickets \
  -H 'Content-Type: application/json' \
  -d '{"title":"Login issue","priority":"HIGH","status":"OPEN"}' | jq

curl -s 'http://localhost:8080/api/v1/tickets?page=0&size=20&sort=createdAt,desc' | jq
```

### API docs (when running)

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Project structure

Classes are grouped by technical role (see [constitution](.specify/memory/constitution.md)):

```text
src/main/java/org/ticketflow/be/
├── controller/      REST controllers
├── service/         Business logic
├── repository/      Spring Data JPA
├── domain/          Entities, enums, domain exceptions
├── dto/             Request/response types
├── mapper/          Entity ↔ DTO mapping
├── exception/       ApiError, global exception handler
└── config/          Web, OpenAPI, pageable defaults
```

Call flow: **controller → service → repository**.

## Spec-Driven Development

| Artifact | Location |
|----------|----------|
| Constitution | [`.specify/memory/constitution.md`](.specify/memory/constitution.md) |
| Feature specs | `specs/001-create-ticket` … `specs/012-automated-testing` |
| Canonical data model | [`specs/002-list-tickets/data-model.md`](specs/002-list-tickets/data-model.md) |
| List feature quickstart | [`specs/002-list-tickets/quickstart.md`](specs/002-list-tickets/quickstart.md) |

Implement from `spec.md` → `plan.md` → `tasks.md` per project governance.

## Configuration

| Profile | File | Database |
|---------|------|----------|
| default | `application.properties` | Flyway on |
| `dev` | `application-dev.properties` | PostgreSQL |
| `test` | `application-test.properties` | In-memory H2 |

Do not commit secrets; override datasource settings via environment or local untracked config.

## Ticket status lifecycle

Updates enforce this state machine (invalid transitions return `400` with a `status` field error):

```text
OPEN → IN_PROGRESS → RESOLVED → CLOSED
OPEN → CANCELLED
IN_PROGRESS → CANCELLED
```

Terminal states (`CLOSED`, `CANCELLED`) cannot transition elsewhere (e.g. `CLOSED → OPEN` is rejected).

## Error responses

Validation and domain errors return JSON:

```json
{
  "code": "VALIDATION_FAILED",
  "message": "Validation failed",
  "fieldErrors": [{ "field": "title", "message": "..." }]
}
```

## License

Add your license here before publishing the repository.