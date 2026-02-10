package com.ivpulse.entity;

import com.ivpulse.entity.enums.HiringStage;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "hiring_status", indexes = { @Index(name = "idx_hiring_res", columnList = "resource_id"),
		@Index(name = "idx_hiring_eng", columnList = "engagement_id"),
		@Index(name = "idx_hiring_stage", columnList = "hiring_stage") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HiringStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "resource_id", nullable = false, columnDefinition = "uuid")
	private ResourceEntity resource;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "engagement_id", columnDefinition = "uuid")
	private Engagement engagement;

	@Enumerated(EnumType.STRING)
	@Column(name = "hiring_stage", length = 30, nullable = false)
	private HiringStage hiringStage;

	@Column(name = "offer_approved")
	private Boolean offerApproved;

	@Column(name = "expected_joining_date")
	private LocalDate expectedJoiningDate;

	@Column(name = "actual_joining_date")
	private LocalDate actualJoiningDate;

	@Column(name = "last_updated_by", length = 200)
	private String lastUpdatedBy;

	@Column(name = "last_updated_at")
	private OffsetDateTime lastUpdatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ResourceEntity getResource() {
		return resource;
	}

	public void setResource(ResourceEntity resource) {
		this.resource = resource;
	}

	public Engagement getEngagement() {
		return engagement;
	}

	public void setEngagement(Engagement engagement) {
		this.engagement = engagement;
	}

	public HiringStage getHiringStage() {
		return hiringStage;
	}

	public void setHiringStage(HiringStage hiringStage) {
		this.hiringStage = hiringStage;
	}

	public Boolean getOfferApproved() {
		return offerApproved;
	}

	public void setOfferApproved(Boolean offerApproved) {
		this.offerApproved = offerApproved;
	}

	public LocalDate getExpectedJoiningDate() {
		return expectedJoiningDate;
	}

	public void setExpectedJoiningDate(LocalDate expectedJoiningDate) {
		this.expectedJoiningDate = expectedJoiningDate;
	}

	public LocalDate getActualJoiningDate() {
		return actualJoiningDate;
	}

	public void setActualJoiningDate(LocalDate actualJoiningDate) {
		this.actualJoiningDate = actualJoiningDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public OffsetDateTime getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(OffsetDateTime lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
}
