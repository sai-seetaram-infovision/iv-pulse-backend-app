-- ETL METADATA / JOB AUDIT

CREATE TABLE etl_job_run (
    id BIGSERIAL PRIMARY KEY,

    job_type VARCHAR(20) NOT NULL,

    status VARCHAR(20) NOT NULL,

    started_at TIMESTAMPTZ NOT NULL,

    finished_at TIMESTAMPTZ,

    processed_count INTEGER,

    error_count INTEGER,

    message VARCHAR(2000)
);

-- Indexes
CREATE INDEX idx_job_type
    ON etl_job_run (job_type);

CREATE INDEX idx_job_status
    ON etl_job_run (status);

CREATE INDEX idx_job_started
    ON etl_job_run (started_at);
