CREATE TABLE ticket (
    id UUID PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(10000),
    priority VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    assignee VARCHAR(320),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_ticket_status ON ticket (status);
CREATE INDEX idx_ticket_created_at ON ticket (created_at);