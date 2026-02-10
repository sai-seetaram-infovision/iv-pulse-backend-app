package com.ivpulse.etl.job;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "etl_job_run", indexes = { @Index(name = "idx_job_type", columnList = "job_type"),
		@Index(name = "idx_job_status", columnList = "status"),
		@Index(name = "idx_job_started", columnList = "started_at") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtlJobRun {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "job_type", length = 20, nullable = false)
	private EtlJobType jobType;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private EtlJobStatus status;

	@Column(name = "started_at", nullable = false)
	private OffsetDateTime startedAt;

	@Column(name = "finished_at")
	private OffsetDateTime finishedAt;

	@Column(name = "processed_count")
	private Integer processedCount;

	@Column(name = "error_count")
	private Integer errorCount;

	@Column(name = "message", length = 2000)
	private String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EtlJobType getJobType() {
		return jobType;
	}

	public void setJobType(EtlJobType jobType) {
		this.jobType = jobType;
	}

	public EtlJobStatus getStatus() {
		return status;
	}

	public void setStatus(EtlJobStatus status) {
		this.status = status;
	}

	public OffsetDateTime getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(OffsetDateTime startedAt) {
		this.startedAt = startedAt;
	}

	public OffsetDateTime getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(OffsetDateTime finishedAt) {
		this.finishedAt = finishedAt;
	}

	public Integer getProcessedCount() {
		return processedCount;
	}

	public void setProcessedCount(Integer processedCount) {
		this.processedCount = processedCount;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
