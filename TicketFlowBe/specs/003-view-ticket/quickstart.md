# Quickstart: View Ticket

**Prerequisites**: Backend running (`./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`). See [002-list-tickets/quickstart.md](../002-list-tickets/quickstart.md).

## Validate `003-view-ticket`

```bash
curl -s http://localhost:8080/api/v1/tickets/{uuid} | jq
```
Expect description + comments array ordered oldest first.
