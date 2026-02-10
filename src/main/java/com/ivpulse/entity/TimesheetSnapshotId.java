package com.ivpulse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimesheetSnapshotId implements Serializable {

	@Column(name = "resource_id", columnDefinition = "uuid", nullable = false)
	private UUID resourceId;

	@Column(name = "engagement_id", columnDefinition = "uuid", nullable = false)
	private UUID engagementId;

	/** Period stored as 'YYYY-MM' */
	@Column(name = "period", length = 7, nullable = false)
	private String period;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TimesheetSnapshotId that))
			return false;
		return Objects.equals(resourceId, that.resourceId) && Objects.equals(engagementId, that.engagementId)
				&& Objects.equals(period, that.period);
	}

	@Override
	public int hashCode() {
		return Objects.hash(resourceId, engagementId, period);
	}

	public UUID getResourceId() {
		return resourceId;
	}

	public void setResourceId(UUID resourceId) {
		this.resourceId = resourceId;
	}

	public UUID getEngagementId() {
		return engagementId;
	}

	public void setEngagementId(UUID engagementId) {
		this.engagementId = engagementId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
