package com.ivpulse.etl.staging;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "staging_record", uniqueConstraints = @UniqueConstraint(name = "uk_staging_dedupe", columnNames = {
		"entity_name", "source_id", "payload_hash" }), indexes = {
				@Index(name = "idx_staging_entity", columnList = "entity_name"),
				@Index(name = "idx_staging_status", columnList = "status"),
				@Index(name = "idx_staging_partition_date", columnList = "partition_date"),
				@Index(name = "idx_staging_synced_at", columnList = "synced_at") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StagingRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "entity_name", length = 40, nullable = false)
	private StagingEntity entityName;

	@Column(name = "source_id", length = 100, nullable = false)
	private String sourceId;

	@JdbcTypeCode(SqlTypes.JSON)
//	@Column(name = "payload", nullable = false, columnDefinition = "TEXT")

	@Column(name = "payload", columnDefinition = "jsonb", nullable = false)
	private String payload; // raw JSON

	@Column(name = "payload_hash", length = 64, nullable = false)
	private String payloadHash; // sha256 hex to dedupe

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 20, nullable = false)
	private StagingStatus status;

	@Column(name = "error_message", length = 2000)
	private String errorMessage;

	@CreationTimestamp
	@Column(name = "synced_at", nullable = false)
	private OffsetDateTime syncedAt;

	@Column(name = "processed_at")
	private OffsetDateTime processedAt;

	@Column(name = "partition_date")
	private LocalDate partitionDate; // for housekeeping/pruning

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StagingEntity getEntityName() {
		return entityName;
	}

	public void setEntityName(StagingEntity entityName) {
		this.entityName = entityName;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getPayloadHash() {
		return payloadHash;
	}

	public void setPayloadHash(String payloadHash) {
		this.payloadHash = payloadHash;
	}

	public StagingStatus getStatus() {
		return status;
	}

	public void setStatus(StagingStatus status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public OffsetDateTime getSyncedAt() {
		return syncedAt;
	}

	public void setSyncedAt(OffsetDateTime syncedAt) {
		this.syncedAt = syncedAt;
	}

	public OffsetDateTime getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(OffsetDateTime processedAt) {
		this.processedAt = processedAt;
	}

	public LocalDate getPartitionDate() {
		return partitionDate;
	}

	public void setPartitionDate(LocalDate partitionDate) {
		this.partitionDate = partitionDate;
	}
}
