<!--
Sync Impact Report
- Version change: 1.1.0 → 1.2.0
- Modified principles: IV (role-based controller/service/repository); Backend Package Layout replaced
- Added sections: none
- Removed sections: api/application/infrastructure package tiers
- Follow-up TODOs: none
-->

# TicketFlow Backend Constitution

## Core Principles

### I. Spec-Driven Development (NON-NEGOTIABLE)

Every feature MUST follow the Spec Kit artifact chain before implementation:
`spec.md` → `plan.md` → `tasks.md` → code. No application code, migrations, or API
surface changes ship without a corresponding, reviewed spec and an approved plan.
Specs state **what** and **why** (user value, acceptance criteria); plans state
**how** within constitution bounds. AI-generated changes MUST trace to explicit
tasks and MUST NOT invent scope beyond the spec.

**Rationale**: Ticket workflows (status, assignment, comments) have clear
acceptance criteria; skipping specs causes duplicate APIs, inconsistent errors,
and untestable scope creep.

### II. REST API Contract Discipline

The backend exposes a REST JSON API with stable, documented shapes. Resources
use sensible HTTP verbs and status codes; validation failures and domain errors
return structured, client-actionable error bodies (field-level where applicable),
not opaque 500s for predictable cases. Breaking API changes require a spec
amendment and version or migration notes in the plan.

**Rationale**: A separate UI depends on predictable contracts; ticket CRUD, search,
and filters fail in production when error and payload shapes drift.

### III. Test-Backed Behavior Changes

Behavior changes MUST include automated tests that fail without the change and
pass with it. API behavior MUST be covered with Spring MVC tests (`MockMvc` or
equivalent); persistence and repository rules MUST have integration tests using
the test database profile. Tests MUST run via `mvn test` with no manual steps.

**Rationale**: Regressions in search, filters, validation, and status transitions
are high-impact; tests are the enforceable definition of done.

### IV. Layered Architecture (Role-Based Packages)

Code is grouped by **technical role** under `org.ticketflow.be`:

| Package | Role | Responsibility | MUST NOT |
|---------|------|----------------|----------|
| **`controller/`** | Presentation | REST mapping, request validation (`@Valid`), call services | Business rules, JPA, SQL |
| **`service/`** | Application | Use cases, transactions, business rules | HTTP types, direct SQL |
| **`repository/`** | Persistence | Spring Data repositories, queries | Business rules, HTTP |

Supporting packages (same root):

- **`domain/`** — entities, enums, domain exceptions (JPA entities live here).
- **`dto/`** — API request/response types used by controllers.
- **`mapper/`** — entity ↔ DTO mapping (no business logic).
- **`exception/`** — `ApiError`, `@RestControllerAdvice`, shared API exceptions.
- **`config/`** — Spring configuration (Web, OpenAPI, pageable limits).

**Call flow**: `controller` → `service` → `repository` → `domain`. Controllers
MUST NOT inject repositories. Services MUST NOT depend on controller or dto types
except where DTOs are passed in as method parameters from the controller.

**Rationale**: Familiar Spring Boot layout; easy navigation by role; keeps REST,
logic, and data access separated.

### V. Simplicity and Incremental Delivery

Prefer the smallest change that satisfies the spec. YAGNI applies: no frameworks,
patterns, or infrastructure beyond current needs. Complexity MUST be justified in
the plan with a simpler alternative rejected explicitly. Features ship in vertical
slices (API + persistence + tests) per task, not horizontal “layers only” PRs.

**Rationale**: A greenfield ticket system gains more from working slices than from
premature abstraction.

## Technology & Security Constraints

- **Runtime**: Java 21, Maven, Spring Boot (Web, Data JPA, Bean Validation).
- **Data**: PostgreSQL for non-test environments; H2 ONLY under the dedicated test
  profile (or file-based H2 for local dev if documented in `application` config).
  Production MUST NOT depend on H2.
- **API style**: REST + JSON; OpenAPI or equivalent contract documentation MUST be
  updated when public endpoints change.
- **Security**: No secrets, credentials, or tokens in source control. Use
  environment-specific configuration. Validate and sanitize all inputs on the
  server; never trust client-only validation. Apply least privilege for data access.
- **Observability**: Use structured logging for request and domain failures;
  include correlation identifiers where the framework supports them.

## Backend Package Layout

```text
src/main/java/org/ticketflow/be/
├── TicketFlowBeApplication.java
├── controller/                 # @RestController
├── service/                    # @Service
├── repository/                 # JpaRepository interfaces
├── domain/                     # @Entity, enums, domain exceptions
├── dto/                        # API request/response records
├── mapper/                     # TicketMapper, etc.
├── exception/                  # GlobalExceptionHandler, ApiError
└── config/                     # WebConfig, OpenApiConfig

src/main/resources/
├── application.properties
├── application-dev.properties
├── application-test.properties
└── db/migration/               # Flyway

src/test/java/org/ticketflow/be/
├── controller/                 # MockMvc integration tests
└── support/                    # IntegrationTestBase
```

Plans and `tasks.md` MUST reference these paths for new classes.

## SDD Workflow & Quality Gates

1. **Constitution** — This file; amended only via `/speckit-constitution`.
2. **Specify** — `/speckit-specify` creates `specs/<feature>/spec.md` with testable
   requirements and measurable success criteria (technology-agnostic in the spec).
3. **Clarify** — `/speckit-clarify` resolves open questions before planning when
   markers remain.
4. **Plan** — `/speckit-plan` produces design and constraints aligned with this
   constitution.
5. **Tasks** — `/speckit-tasks` yields ordered, independently verifiable work items.
6. **Implement** — `/speckit-implement` (or equivalent) executes tasks; each PR
   references its feature directory and satisfies linked tasks.

**Gates**: Spec checklist complete before plan; plan reviewed before tasks;
`mvn test` green before merge; no merge with failing tests or undeclared API
breaks. AI assistants (Cursor, Copilot, etc.) MUST read this constitution and the
active feature spec before generating code.

## Governance

This constitution supersedes ad-hoc conventions and informal README guidance for
all Spec Kit artifacts and implementation work on TicketFlowBe. Amendments use
`/speckit-constitution`, bump the version per semver below, and update
`LAST_AMENDED_DATE`. Reviewers MUST verify PR compliance with applicable
principles and the feature spec.

**Amendment policy**:

- **MAJOR**: Removal or incompatible redefinition of a principle.
- **MINOR**: New principle or materially expanded section.
- **PATCH**: Clarifications, wording, non-semantic edits.

Specs and plans that conflict with this document MUST be revised or the
constitution amended first—implementation MUST NOT proceed on conflict.

**Version**: 1.2.0 | **Ratified**: 2026-11-20 | **Last Amended**: 2026-11-20