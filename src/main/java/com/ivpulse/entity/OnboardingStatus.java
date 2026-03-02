package com.ivpulse.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "onboarding_status", indexes = { @Index(name = "idx_onb_res", columnList = "resource_id"),
		@Index(name = "idx_onb_bgv", columnList = "bgv_status") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnboardingStatus {

	@Id
	@Column(name = "onboarding_status_id", columnDefinition = "uuid")
	private UUID onboardingStatusId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "resource_id", nullable = false, columnDefinition = "uuid")
	private ResourceEntity resource;

	@Column(name = "induction_status", length = 20)
	private String inductionStatus;

	@Column(name = "bgv_status", length = 20)
	private String bgvStatus;

	@Column(name = "client_access_status", length = 20)
	private String clientAccessStatus;

	@Column(name = "work_order_status", length = 20)
	private String workOrderStatus;

	@Column(name = "comments", length = 2000)
	private String comments;

	@Column(name = "source_last_modified")
	private LocalDateTime sourceLastModified;

	@Column(name = "source_system", length = 50)
	private String sourceSystem;

	public UUID getOnboardingStatusId() {
		return onboardingStatusId;
	}

	public void setOnboardingStatusId(UUID onboardingStatusId) {
		this.onboardingStatusId = onboardingStatusId;
	}

	public ResourceEntity getResource() {
		return resource;
	}

	public void setResource(ResourceEntity resource) {
		this.resource = resource;
	}

	public String getInductionStatus() {
		return inductionStatus;
	}

	public void setInductionStatus(String inductionStatus) {
		this.inductionStatus = inductionStatus;
	}

	public String getBgvStatus() {
		return bgvStatus;
	}

	public void setBgvStatus(String bgvStatus) {
		this.bgvStatus = bgvStatus;
	}

	public String getClientAccessStatus() {
		return clientAccessStatus;
	}

	public void setClientAccessStatus(String clientAccessStatus) {
		this.clientAccessStatus = clientAccessStatus;
	}

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDateTime getSourceLastModified() {
		return sourceLastModified;
	}

	public void setSourceLastModified(LocalDateTime sourceLastModified) {
		this.sourceLastModified = sourceLastModified;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

}
