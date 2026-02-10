
-- CORE NORMALIZED TABLES (Phase 8–10)

CREATE TABLE client (
    client_id UUID PRIMARY KEY,

    client_code VARCHAR(100),

    client_name VARCHAR(250) NOT NULL,

    region VARCHAR(100),

    industry VARCHAR(100),

    status VARCHAR(20) NOT NULL,
    
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Indexes
CREATE INDEX idx_client_code
    ON client (client_code);

CREATE INDEX idx_client_status
    ON client (status);
    
ALTER TABLE client
ADD COLUMN cost_center varchar(100);


CREATE TABLE engagement (
    engagement_id UUID PRIMARY KEY,

    client_id UUID NOT NULL
        REFERENCES client(client_id),

    engagement_code VARCHAR(100),

    engagement_name VARCHAR(300) NOT NULL,

    start_date DATE,

    end_date_planned DATE,

    engagement_status VARCHAR(20) NOT NULL,

    billing_currency VARCHAR(10),

    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Indexes
CREATE INDEX idx_eng_client
    ON engagement (client_id);

CREATE INDEX idx_eng_code
    ON engagement (engagement_code);

CREATE INDEX idx_eng_status
    ON engagement (engagement_status);


CREATE TABLE role_master (
    role_id UUID PRIMARY KEY,

    role_name VARCHAR(200) NOT NULL,

    role_category VARCHAR(200),

    active_flag BOOLEAN NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Index
CREATE INDEX idx_role_active
    ON role_master (active_flag);


CREATE TABLE resource (
    resource_id UUID PRIMARY KEY,

    external_employee_id VARCHAR(100),

    full_name VARCHAR(250),

    email VARCHAR(250) UNIQUE,

    employment_type VARCHAR(20),

    base_location VARCHAR(100),

    grade_level VARCHAR(100),

    primary_skill VARCHAR(200),

    joining_date DATE,

    exit_date DATE,

    employment_status VARCHAR(20) NOT NULL,

    source_system VARCHAR(50),

    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Indexes
CREATE INDEX idx_resource_email
    ON resource (email);

CREATE INDEX idx_resource_status
    ON resource (employment_status);

CREATE INDEX idx_resource_join_exit
    ON resource (joining_date, exit_date);


CREATE TABLE engagement_resource (
    engagement_resource_id UUID PRIMARY KEY,

    engagement_id UUID NOT NULL
        REFERENCES engagement(engagement_id),

    resource_id UUID NOT NULL
        REFERENCES resource(resource_id),

    role_id UUID
        REFERENCES role_master(role_id),

    allocation_percentage INTEGER,

    allocation_start_date DATE,

    allocation_end_date DATE,

    billing_status VARCHAR(20),

    billing_start_date DATE,

    billing_end_date DATE,

    client_manager VARCHAR(200),

    internal_manager VARCHAR(200),

    active_flag BOOLEAN,

    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Indexes
CREATE INDEX idx_er_eng
    ON engagement_resource (engagement_id);

CREATE INDEX idx_er_res
    ON engagement_resource (resource_id);

CREATE INDEX idx_er_dates
    ON engagement_resource (allocation_start_date, allocation_end_date);

CREATE INDEX idx_er_billing_status
    ON engagement_resource (billing_status);


CREATE TABLE rate_card (
    rate_id UUID PRIMARY KEY,

    engagement_id UUID NOT NULL
        REFERENCES engagement(engagement_id),

    role_id UUID
        REFERENCES role_master(role_id),

    rate_amount NUMERIC(18,2),

    rate_type VARCHAR(20),

    effective_from DATE,

    effective_to DATE,

    reference_only BOOLEAN NOT NULL
);

-- Indexes
CREATE INDEX idx_rate_eng
    ON rate_card (engagement_id);

CREATE INDEX idx_rate_role
    ON rate_card (role_id);


CREATE TABLE IF NOT EXISTS hiring_status (
    id BIGSERIAL PRIMARY KEY,

    resource_id UUID NOT NULL
        REFERENCES resource(resource_id),

    engagement_id UUID
        REFERENCES engagement(engagement_id),

    hiring_stage VARCHAR(30) NOT NULL,

    offer_approved BOOLEAN,

    expected_joining_date DATE,

    actual_joining_date DATE,

    last_updated_by VARCHAR(200),

    last_updated_at TIMESTAMPTZ
);

-- Indexes
CREATE INDEX idx_hiring_res
    ON hiring_status (resource_id);

CREATE INDEX idx_hiring_eng
    ON hiring_status (engagement_id);

CREATE INDEX idx_hiring_stage
    ON hiring_status (hiring_stage);


CREATE TABLE IF NOT EXISTS billing_snapshot (
    id BIGSERIAL PRIMARY KEY,

    engagement_resource_id UUID NOT NULL
        REFERENCES engagement_resource(engagement_resource_id),

    expected_hours NUMERIC(18,2),

    actual_hours NUMERIC(18,2),

    billing_ready_flag BOOLEAN,

    unbilled_reason VARCHAR(500),

    snapshot_date DATE
);

-- Indexes
CREATE INDEX idx_billing_er
    ON billing_snapshot (engagement_resource_id);

CREATE INDEX idx_billing_date
    ON billing_snapshot (snapshot_date);

CREATE INDEX idx_billing_ready
    ON billing_snapshot (billing_ready_flag);


CREATE TABLE IF NOT EXISTS timesheet_snapshot (
    ts_id SERIAL PRIMARY KEY,
    year_month TEXT NOT NULL,
    engagement_id UUID REFERENCES engagement(engagement_id),
    engagement_resource_id UUID REFERENCES engagement_resource(engagement_resource_id),
    resource_id UUID REFERENCES resource(resource_id),
    submitted_flag BOOLEAN,
    approved_flag BOOLEAN,
    approval_date DATE,
    total_hours NUMERIC,
    created_at TIMESTAMP DEFAULT now()
);
