# Quickstart: Validate TicketFlow (list focus)

**Prerequisites**

- JDK 21, Maven 3.9+
- PostgreSQL 15+ (local) **or** profile that uses existing H2 for first boot only
- Optional: Node 20+ for React/Next UI in separate repo

**Profiles**

| Profile | Database |
|---------|----------|
| `test` | H2 in-memory (`mvn test`) |
| `dev` | PostgreSQL (`application-dev.properties`) |
| default local | Document in tasks—point at dev or docker-compose |

## 1. Start PostgreSQL (dev)

```bash
# Example: container (adjust credentials to match application-dev.properties)
docker run -d --name ticketflow-pg -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=ticketflow -p 5432:5432 postgres:16
```

## 2. Run backend

```bash
cd /Users/anubhavkhatri/WebstormProjects/TicketFlow/TicketFlowBe
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

After implementation, API base: `http://localhost:8080/api/v1`

**Implementation status (2026-11-20)**: Backend endpoints implemented; run `./mvnw test` with **JDK 21** (project `java.version` in `pom.xml`).

## 3. Validate list feature (002)

**Setup data**: Create at least 25 tickets via `POST /api/v1/tickets` (see contract).

**Happy path — first page, default sort**

```bash
curl -s "http://localhost:8080/api/v1/tickets?page=0&size=20&sort=createdAt,desc" | jq
```

**Expected**: JSON with `content[]` (summaries only: no description), `totalElements`,
`totalPages`, `page`, `size`; default newest first.

**Sort by priority**

```bash
curl -s "http://localhost:8080/api/v1/tickets?sort=priority,desc&size=5" | jq
```

**Expected**: URGENT/HIGH before LOW/MEDIUM per data-model priority ordering.

**Pagination stability**

```bash
curl -s "http://localhost:8080/api/v1/tickets?page=0&size=10&sort=createdAt,asc" | jq '.content[].id'
curl -s "http://localhost:8080/api/v1/tickets?page=1&size=10&sort=createdAt,asc" | jq '.content[].id'
```

**Expected**: No duplicate ids across pages when data unchanged.

**Invalid sort**

```bash
curl -s -w "\n%{http_code}" "http://localhost:8080/api/v1/tickets?sort=invalid,desc"
```

**Expected**: `400` with `ApiError` and field/message for sort.

**Empty system**

```bash
curl -s "http://localhost:8080/api/v1/tickets" | jq '.totalElements'
```

**Expected**: `0`, `content: []`.

## 4. Automated tests

```bash
./mvnw test
```

**Expected**: Green; includes MockMvc list tests and H2 integration (per spec `012`).

## 5. OpenAPI

When springdoc is added (tasks phase):

- UI: `http://localhost:8080/swagger-ui.html`
- Spec file: `specs/002-list-tickets/contracts/openapi.yaml` (source of truth for reviews)

## 6. Frontend (React/Next)

Separate client points at `http://localhost:8080`; enable CORS for `http://localhost:5173`
(Vite) or `http://localhost:3000` (Next). UI notifications (spec `011`) map `ApiError`
to toasts per `010-api-error-handling`.