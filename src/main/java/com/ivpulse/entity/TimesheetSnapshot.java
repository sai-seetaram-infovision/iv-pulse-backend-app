package com.ivpulse.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "timesheet_snapshot", indexes = { @Index(name = "idx_ts_by_period", columnList = "period"),
		@Index(name = "idx_ts_by_res", columnList = "resource_id"),
		@Index(name = "idx_ts_by_eng", columnList = "engagement_id") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimesheetSnapshot {

	@EmbeddedId
	private TimesheetSnapshotId id;

	@Column(name = "submitted_flag")
	private Boolean submittedFlag;

	@Column(name = "approved_flag")
	private Boolean approvedFlag;

	@Column(name = "approval_date")
	private LocalDate approvalDate;

	@Column(name = "source_system", length = 50)
	private String sourceSystem;

	@CreationTimestamp
	@Column(name = "synced_at", nullable = false)
	private OffsetDateTime syncedAt;

	public TimesheetSnapshotId getId() {
		return id;
	}

	public void setId(TimesheetSnapshotId id) {
		this.id = id;
	}

	public Boolean getSubmittedFlag() {
		return submittedFlag;
	}

	public void setSubmittedFlag(Boolean submittedFlag) {
		this.submittedFlag = submittedFlag;
	}

	public Boolean getApprovedFlag() {
		return approvedFlag;
	}

	public void setApprovedFlag(Boolean approvedFlag) {
		this.approvedFlag = approvedFlag;
	}

	public LocalDate getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDate approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public OffsetDateTime getSyncedAt() {
		return syncedAt;
	}

	public void setSyncedAt(OffsetDateTime syncedAt) {
		this.syncedAt = syncedAt;
	}

//	// Convenience accessors
//	public String getPeriod() {
//		return id != null ? id.getPeriod() : null;
//	}
//
//	public void setPeriod(String p) {
//		if (id == null)
//			id = new TimesheetSnapshotId();
//		id.setPeriod(p);
//	}
//
//	public java.util.UUID getResourceId() {
//		return id != null ? id.getResourceId() : null;
//	}
//
//	public void setResourceId(java.util.UUID r) {
//		if (id == null)
//			id = new TimesheetSnapshotId();
//		id.setResourceId(r);
//	}
//
//	public java.util.UUID getEngagementId() {
//		return id != null ? id.getEngagementId() : null;
//	}

//	public void setEngagementId(java.util.UUID e) {
//		if (id == null)
//			id = new TimesheetSnapshotId();
//		id.setEngagementId(e);
//	}
}
