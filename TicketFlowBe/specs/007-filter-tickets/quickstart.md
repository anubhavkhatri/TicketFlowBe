# Quickstart: Filter Tickets

**Prerequisites**: Backend running (`./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`). See [002-list-tickets/quickstart.md](../002-list-tickets/quickstart.md).

## Validate `007-filter-tickets`

```bash
curl -s 'http://localhost:8080/api/v1/tickets?status=OPEN&status=IN_PROGRESS' | jq
```
