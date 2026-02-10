
-- BGV SNAPSHOT TABLE + INDEXES

CREATE TABLE bgv_status (
    bgv_id SERIAL PRIMARY KEY,
    resource_id UUID REFERENCES resource(resource_id),
    bgv_vendor TEXT,
    status TEXT,
    requested_on DATE,
    verified_on DATE,
    remarks TEXT,
    created_at TIMESTAMP DEFAULT now()
);

CREATE INDEX idx_bgv_resource ON bgv_status(resource_id);
