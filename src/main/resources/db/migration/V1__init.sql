CREATE TABLE compliance (
    id BIGSERIAL PRIMARY KEY,

    title VARCHAR(255) NOT NULL,
    description TEXT,

    status VARCHAR(50) NOT NULL,
    score INTEGER,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_status ON compliance(status);
CREATE INDEX idx_created_at ON compliance(created_at);