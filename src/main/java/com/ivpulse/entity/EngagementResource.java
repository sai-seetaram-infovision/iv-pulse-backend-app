package com.ivpulse.entity;

import com.ivpulse.entity.enums.BillingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "engagement_resource", indexes = { @Index(name = "idx_er_eng", columnList = "engagement_id"),
		@Index(name = "idx_er_res", columnList = "resource_id"),
		@Index(name = "idx_er_dates", columnList = "allocation_start_date,allocation_end_date"),
		@Index(name = "idx_er_billing_status", columnList = "billing_status") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EngagementResource {
	@Id
	@Column(name = "engagement_resource_id", columnDefinition = "uuid")
	private UUID engagementResourceId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "engagement_id", nullable = false)
	private Engagement engagement;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "resource_id", nullable = false)
	private ResourceEntity resource;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private RoleMaster role;

	@Column(name = "allocation_percentage")
	private Integer allocationPercentage; // 0–100

	@Column(name = "allocation_start_date")
	private LocalDate allocationStartDate;

	@Column(name = "allocation_end_date")
	private LocalDate allocationEndDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "billing_status", length = 20)
	private BillingStatus billingStatus; // Billable/Non-Billable/Bench

	@Column(name = "billing_start_date")
	private LocalDate billingStartDate;

	@Column(name = "billing_end_date")
	private LocalDate billingEndDate;

	@Column(name = "client_manager", length = 200)
	private String clientManager;

	@Column(name = "internal_manager", length = 200)
	private String internalManager;

	@Column(name = "active_flag")
	private Boolean activeFlag;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	public UUID getEngagementResourceId() {
		return engagementResourceId;
	}

	public void setEngagementResourceId(UUID engagementResourceId) {
		this.engagementResourceId = engagementResourceId;
	}

	public Engagement getEngagement() {
		return engagement;
	}

	public void setEngagement(Engagement engagement) {
		this.engagement = engagement;
	}

	public ResourceEntity getResource() {
		return resource;
	}

	public void setResource(ResourceEntity resource) {
		this.resource = resource;
	}

	public RoleMaster getRole() {
		return role;
	}

	public void setRole(RoleMaster role) {
		this.role = role;
	}

	public Integer getAllocationPercentage() {
		return allocationPercentage;
	}

	public void setAllocationPercentage(Integer allocationPercentage) {
		this.allocationPercentage = allocationPercentage;
	}

	public LocalDate getAllocationStartDate() {
		return allocationStartDate;
	}

	public void setAllocationStartDate(LocalDate allocationStartDate) {
		this.allocationStartDate = allocationStartDate;
	}

	public LocalDate getAllocationEndDate() {
		return allocationEndDate;
	}

	public void setAllocationEndDate(LocalDate allocationEndDate) {
		this.allocationEndDate = allocationEndDate;
	}

	public BillingStatus getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(BillingStatus billingStatus) {
		this.billingStatus = billingStatus;
	}

	public LocalDate getBillingStartDate() {
		return billingStartDate;
	}

	public void setBillingStartDate(LocalDate billingStartDate) {
		this.billingStartDate = billingStartDate;
	}

	public LocalDate getBillingEndDate() {
		return billingEndDate;
	}

	public void setBillingEndDate(LocalDate billingEndDate) {
		this.billingEndDate = billingEndDate;
	}

	public String getClientManager() {
		return clientManager;
	}

	public void setClientManager(String clientManager) {
		this.clientManager = clientManager;
	}

	public String getInternalManager() {
		return internalManager;
	}

	public void setInternalManager(String internalManager) {
		this.internalManager = internalManager;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
