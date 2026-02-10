package com.ivpulse.dto.productivv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimesheetDto {
	@JsonProperty("Id")
	private Integer id;
	@JsonProperty("EmployeeId")
	private Integer employeeId;
	@JsonProperty("ProjectId")
	private Integer projectId;
	@JsonProperty("WorkDate")
	private OffsetDateTime workDate;
	@JsonProperty("Hours")
	private BigDecimal hours;
	@JsonProperty("SubmittedAt")
	private OffsetDateTime submittedAt;
	@JsonProperty("ApprovedAt")
	private OffsetDateTime approvedAt;
	@JsonProperty("Status")
	private String status; // Submitted/Approved/Rejected
	
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
	public OffsetDateTime getWorkDate() {
		return workDate;
	}
	public void setWorkDate(OffsetDateTime workDate) {
		this.workDate = workDate;
	}
	public BigDecimal getHours() {
		return hours;
	}
	public void setHours(BigDecimal hours) {
		this.hours = hours;
	}
	public OffsetDateTime getSubmittedAt() {
		return submittedAt;
	}
	public void setSubmittedAt(OffsetDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}
	public OffsetDateTime getApprovedAt() {
		return approvedAt;
	}
	public void setApprovedAt(OffsetDateTime approvedAt) {
		this.approvedAt = approvedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
