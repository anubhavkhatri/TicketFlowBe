# Quickstart: Add Comments

**Prerequisites**: Backend running (`./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`). See [002-list-tickets/quickstart.md](../002-list-tickets/quickstart.md).

## Validate `005-add-comments`

```bash
curl -s -X POST http://localhost:8080/api/v1/tickets/{uuid}/comments \
  -H 'Content-Type: application/json' -d '{"body":"Called customer","author":"agent@co"}' | jq
```
