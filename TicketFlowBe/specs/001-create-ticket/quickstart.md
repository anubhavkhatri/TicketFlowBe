# Quickstart: Create Support Ticket

**Prerequisites**: Backend running (`./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`). See [002-list-tickets/quickstart.md](../002-list-tickets/quickstart.md).

## Validate `001-create-ticket`

## Validate create
```bash
curl -s -X POST http://localhost:8080/api/v1/tickets -H 'Content-Type: application/json' \
  -d '{"title":"Login issue","priority":"HIGH","status":"OPEN"}' | jq
```
Expect 201 and UUID id. Omit title → 400 fieldErrors.
