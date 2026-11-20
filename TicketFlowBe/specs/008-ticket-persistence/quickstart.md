# Quickstart: Ticket and Comment Persistence

**Prerequisites**: Backend running (`./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`). See [002-list-tickets/quickstart.md](../002-list-tickets/quickstart.md).

## Validate `008-ticket-persistence`

```bash
./mvnw test -Dspring.profiles.active=test
```
Expect H2 integration; dev profile uses PostgreSQL env vars.
