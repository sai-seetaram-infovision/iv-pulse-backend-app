-- STAGING LAYER (Phase 6–10)

CREATE TABLE staging_record (
    id BIGSERIAL PRIMARY KEY,

    entity_name VARCHAR(40) NOT NULL,

    source_id VARCHAR(100) NOT NULL,

    payload JSONB NOT NULL,

    payload_hash VARCHAR(64) NOT NULL,

    status VARCHAR(20) NOT NULL,

    error_message VARCHAR(2000),

    synced_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    processed_at TIMESTAMPTZ,

    partition_date DATE
);

---- Deduplication constraint
--ALTER TABLE staging_record
--    ADD CONSTRAINT uk_staging_dedupe
--    UNIQUE (entity_name, source_id, payload_hash);

-- Indexes
CREATE INDEX idx_staging_entity
    ON staging_record (entity_name);

CREATE INDEX idx_staging_status
    ON staging_record (status);

CREATE INDEX idx_staging_partition_date
    ON staging_record (partition_date);

CREATE INDEX idx_staging_synced_at
    ON staging_record (synced_at);
    
    
CREATE TABLE source_sync_state (
    state_key VARCHAR(100) PRIMARY KEY,  -- matches @Id with length 100
    source_type VARCHAR(30) NOT NULL,    -- Enum stored as STRING
    last_from DATE,
    last_to DATE,
    last_synced_at TIMESTAMP,
    notes VARCHAR(1000)
);

