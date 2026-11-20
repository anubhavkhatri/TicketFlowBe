# Quickstart: Search Tickets

**Prerequisites**: Backend running (`./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`). See [002-list-tickets/quickstart.md](../002-list-tickets/quickstart.md).

## Validate `006-search-tickets`

```bash
curl -s 'http://localhost:8080/api/v1/tickets?q=login&page=0&size=20' | jq
```
