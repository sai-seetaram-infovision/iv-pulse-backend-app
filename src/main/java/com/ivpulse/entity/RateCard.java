package com.ivpulse.entity;

import com.ivpulse.entity.enums.RateType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "rate_card", indexes = { @Index(name = "idx_rate_eng", columnList = "engagement_id"),
		@Index(name = "idx_rate_role", columnList = "role_id") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateCard {
	@Id
	@Column(name = "rate_id", columnDefinition = "uuid")
	private UUID rateId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "engagement_id", nullable = false)
	private Engagement engagement;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private RoleMaster role;

	@Column(name = "rate_amount", precision = 18, scale = 2)
	private BigDecimal rateAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "rate_type", length = 20)
	private RateType rateType;

	@Column(name = "effective_from")
	private LocalDate effectiveFrom;

	@Column(name = "effective_to")
	private LocalDate effectiveTo;

	@Column(name = "reference_only", nullable = false)
	private Boolean referenceOnly;

	public UUID getRateId() {
		return rateId;
	}

	public void setRateId(UUID rateId) {
		this.rateId = rateId;
	}

	public Engagement getEngagement() {
		return engagement;
	}

	public void setEngagement(Engagement engagement) {
		this.engagement = engagement;
	}

	public RoleMaster getRole() {
		return role;
	}

	public void setRole(RoleMaster role) {
		this.role = role;
	}

	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

	public RateType getRateType() {
		return rateType;
	}

	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

	public LocalDate getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(LocalDate effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public LocalDate getEffectiveTo() {
		return effectiveTo;
	}

	public void setEffectiveTo(LocalDate effectiveTo) {
		this.effectiveTo = effectiveTo;
	}

	public Boolean getReferenceOnly() {
		return referenceOnly;
	}

	public void setReferenceOnly(Boolean referenceOnly) {
		this.referenceOnly = referenceOnly;
	}
}
