package com.ivpulse.etl.kv;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "source_sync_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceSyncState {

	/** key example: employees, timesheets-current, timesheets-lastMonth */
	@Id
	@Column(name = "state_key", length = 100)
	private String stateKey;

	@Enumerated(EnumType.STRING)
	@Column(name = "source_type", length = 30, nullable = false)
	private SourceType sourceType;

	@Column(name = "last_from")
	private LocalDate lastFrom;

	@Column(name = "last_to")
	private LocalDate lastTo;

	@Column(name = "last_synced_at")
	private OffsetDateTime lastSyncedAt;

	@Column(name = "notes", length = 1000)
	private String notes;

	public String getStateKey() {
		return stateKey;
	}

	public void setStateKey(String stateKey) {
		this.stateKey = stateKey;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public LocalDate getLastFrom() {
		return lastFrom;
	}

	public void setLastFrom(LocalDate lastFrom) {
		this.lastFrom = lastFrom;
	}

	public LocalDate getLastTo() {
		return lastTo;
	}

	public void setLastTo(LocalDate lastTo) {
		this.lastTo = lastTo;
	}

	public OffsetDateTime getLastSyncedAt() {
		return lastSyncedAt;
	}

	public void setLastSyncedAt(OffsetDateTime lastSyncedAt) {
		this.lastSyncedAt = lastSyncedAt;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
