CREATE TABLE comment (
    id UUID PRIMARY KEY,
    ticket_id UUID NOT NULL REFERENCES ticket (id),
    body VARCHAR(5000) NOT NULL,
    author VARCHAR(320) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_comment_ticket_id ON comment (ticket_id);