package com.ivpulse.entity;

import com.ivpulse.entity.enums.BgvStatus;
import com.ivpulse.entity.enums.ClientAccessStatus;
import com.ivpulse.entity.enums.InductionStatus;
import com.ivpulse.entity.enums.WorkOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "resource_id", nullable = false, columnDefinition = "uuid")
	private ResourceEntity resource;

	@Enumerated(EnumType.STRING)
	@Column(name = "induction_status", length = 20)
	private InductionStatus inductionStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "bgv_status", length = 20)
	private BgvStatus bgvStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "client_access_status", length = 20)
	private ClientAccessStatus clientAccessStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "work_order_status", length = 20)
	private WorkOrderStatus workOrderStatus;

	@Column(name = "comments", length = 2000)
	private String comments;

	@Column(name = "last_updated_at")
	private java.time.OffsetDateTime lastUpdatedAt;

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

	public InductionStatus getInductionStatus() {
		return inductionStatus;
	}

	public void setInductionStatus(InductionStatus inductionStatus) {
		this.inductionStatus = inductionStatus;
	}

	public BgvStatus getBgvStatus() {
		return bgvStatus;
	}

	public void setBgvStatus(BgvStatus bgvStatus) {
		this.bgvStatus = bgvStatus;
	}

	public ClientAccessStatus getClientAccessStatus() {
		return clientAccessStatus;
	}

	public void setClientAccessStatus(ClientAccessStatus clientAccessStatus) {
		this.clientAccessStatus = clientAccessStatus;
	}

	public WorkOrderStatus getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(WorkOrderStatus workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public java.time.OffsetDateTime getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(java.time.OffsetDateTime lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
}
