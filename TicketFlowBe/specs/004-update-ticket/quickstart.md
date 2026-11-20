# Quickstart: Update Ticket

**Prerequisites**: Backend running (`./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`). See [002-list-tickets/quickstart.md](../002-list-tickets/quickstart.md).

## Validate `004-update-ticket`

```bash
curl -s -X PATCH http://localhost:8080/api/v1/tickets/{uuid} -H 'Content-Type: application/json' \
  -d '{"status":"RESOLVED"}' | jq
```
