package com.ivpulse.entity;

import com.ivpulse.entity.enums.EngagementStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "engagement", indexes = { @Index(name = "idx_eng_client", columnList = "client_id"),
		@Index(name = "idx_eng_code", columnList = "engagement_code"),
		@Index(name = "idx_eng_status", columnList = "engagement_status") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Engagement {
	@Id
	@Column(name = "engagement_id", columnDefinition = "uuid")
	private UUID engagementId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "client_id", nullable = false)
	private Client client;

	@Column(name = "engagement_code", length = 100)
	private String engagementCode;

	@Column(name = "engagement_name", length = 300, nullable = false)
	private String engagementName;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date_planned")
	private LocalDate endDatePlanned;

	@Enumerated(EnumType.STRING)
	@Column(name = "engagement_status", length = 20, nullable = false)
	private EngagementStatus engagementStatus;

	@Column(name = "billing_currency", length = 10)
	private String billingCurrency;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	public UUID getEngagementId() {
		return engagementId;
	}

	public void setEngagementId(UUID engagementId) {
		this.engagementId = engagementId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getEngagementCode() {
		return engagementCode;
	}

	public void setEngagementCode(String engagementCode) {
		this.engagementCode = engagementCode;
	}

	public String getEngagementName() {
		return engagementName;
	}

	public void setEngagementName(String engagementName) {
		this.engagementName = engagementName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDatePlanned() {
		return endDatePlanned;
	}

	public void setEndDatePlanned(LocalDate endDatePlanned) {
		this.endDatePlanned = endDatePlanned;
	}

	public EngagementStatus getEngagementStatus() {
		return engagementStatus;
	}

	public void setEngagementStatus(EngagementStatus engagementStatus) {
		this.engagementStatus = engagementStatus;
	}

	public String getBillingCurrency() {
		return billingCurrency;
	}

	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
