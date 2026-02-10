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
public class HiringDto {
	@JsonProperty("Id")
	private Integer id;
	@JsonProperty("EmployeeId")
	private Integer employeeId;
	@JsonProperty("ProjectId")
	private Integer projectId;
	@JsonProperty("HiringStage")
	private String hiringStage;
	@JsonProperty("OfferApproved")
	private Boolean offerApproved;
	@JsonProperty("ExpectedJoiningDate")
	private OffsetDateTime expectedJoiningDate;
	@JsonProperty("ActualJoiningDate")
	private OffsetDateTime actualJoiningDate;
	@JsonProperty("LastUpdatedBy")
	private String lastUpdatedBy;
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

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getHiringStage() {
		return hiringStage;
	}

	public void setHiringStage(String hiringStage) {
		this.hiringStage = hiringStage;
	}

	public Boolean getOfferApproved() {
		return offerApproved;
	}

	public void setOfferApproved(Boolean offerApproved) {
		this.offerApproved = offerApproved;
	}

	public OffsetDateTime getExpectedJoiningDate() {
		return expectedJoiningDate;
	}

	public void setExpectedJoiningDate(OffsetDateTime expectedJoiningDate) {
		this.expectedJoiningDate = expectedJoiningDate;
	}

	public OffsetDateTime getActualJoiningDate() {
		return actualJoiningDate;
	}

	public void setActualJoiningDate(OffsetDateTime actualJoiningDate) {
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
