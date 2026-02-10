package com.ivpulse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "billing_snapshot", indexes = { @Index(name = "idx_billing_er", columnList = "engagement_resource_id"),
		@Index(name = "idx_billing_date", columnList = "snapshot_date"),
		@Index(name = "idx_billing_ready", columnList = "billing_ready_flag") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingSnapshot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "engagement_resource_id", nullable = false, columnDefinition = "uuid")
	private EngagementResource engagementResource;

	@Column(name = "expected_hours", precision = 18, scale = 2)
	private BigDecimal expectedHours;

	@Column(name = "actual_hours", precision = 18, scale = 2)
	private BigDecimal actualHours;

	@Column(name = "billing_ready_flag")
	private Boolean billingReadyFlag;

	@Column(name = "unbilled_reason", length = 500)
	private String unbilledReason;

	@Column(name = "snapshot_date")
	private LocalDate snapshotDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EngagementResource getEngagementResource() {
		return engagementResource;
	}

	public void setEngagementResource(EngagementResource engagementResource) {
		this.engagementResource = engagementResource;
	}

	public BigDecimal getExpectedHours() {
		return expectedHours;
	}

	public void setExpectedHours(BigDecimal expectedHours) {
		this.expectedHours = expectedHours;
	}

	public BigDecimal getActualHours() {
		return actualHours;
	}

	public void setActualHours(BigDecimal actualHours) {
		this.actualHours = actualHours;
	}

	public Boolean getBillingReadyFlag() {
		return billingReadyFlag;
	}

	public void setBillingReadyFlag(Boolean billingReadyFlag) {
		this.billingReadyFlag = billingReadyFlag;
	}

	public String getUnbilledReason() {
		return unbilledReason;
	}

	public void setUnbilledReason(String unbilledReason) {
		this.unbilledReason = unbilledReason;
	}

	public LocalDate getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(LocalDate snapshotDate) {
		this.snapshotDate = snapshotDate;
	}
}
