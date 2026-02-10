package com.ivpulse.dto.productivv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingSnapshotDto {
	@JsonProperty("EngagementResourceId")
	private Integer engagementResourceId;
	@JsonProperty("ExpectedHours")
	private BigDecimal expectedHours;
	@JsonProperty("ActualHours")
	private BigDecimal actualHours;
	@JsonProperty("BillingReadyFlag")
	private Boolean billingReadyFlag;
	@JsonProperty("UnbilledReason")
	private String unbilledReason;
	@JsonProperty("SnapshotDate")
	private LocalDateTime snapshotDate;

	public Integer getEngagementResourceId() {
		return engagementResourceId;
	}

	public void setEngagementResourceId(Integer engagementResourceId) {
		this.engagementResourceId = engagementResourceId;
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

	public LocalDateTime getSnapshotDate() {
		return snapshotDate;
	}

	public void setSnapshotDate(LocalDateTime snapshotDate) {
		this.snapshotDate = snapshotDate;
	}
}
