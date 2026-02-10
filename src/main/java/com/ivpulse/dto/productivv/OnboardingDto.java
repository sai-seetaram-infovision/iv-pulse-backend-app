package com.ivpulse.dto.productivv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnboardingDto {
	@JsonProperty("Id")
	private Integer id;
	@JsonProperty("EmployeeId")
	private Integer employeeId;
	@JsonProperty("InductionStatus")
	private String inductionStatus;
	@JsonProperty("BgvStatus")
	private String bgvStatus;
	@JsonProperty("ClientAccessStatus")
	private String clientAccessStatus;
	@JsonProperty("WorkOrderStatus")
	private String workOrderStatus;
	@JsonProperty("Comments")
	private String comments;
	@JsonProperty("LastUpdatedAt")
	private OffsetDateTime lastUpdatedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public OffsetDateTime getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(OffsetDateTime lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
}
