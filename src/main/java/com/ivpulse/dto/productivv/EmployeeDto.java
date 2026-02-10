package com.ivpulse.dto.productivv;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {
	@JsonProperty("Id")
	private Integer id;
	@JsonProperty("InstanceId")
	private Integer instanceId;
	@JsonProperty("EmployeeCode")
	private String employeeCode;
	@JsonProperty("EmployeeCodeNumber")
	private Integer employeeCodeNumber;
	@JsonProperty("FirstName")
	private String firstName;
	@JsonProperty("MiddleName")
	private String middleName;
	@JsonProperty("LastName")
	private String lastName;
	@JsonProperty("EmailId")
	private String emailId;
	@JsonProperty("Gender")
	private String gender;
	@JsonProperty("DateOfJoining")
	private LocalDateTime dateOfJoining;
	@JsonProperty("DateOfResigning")
	private LocalDateTime dateOfResigning;
	@JsonProperty("ReleaseDate")
	private LocalDateTime releaseDate;
	@JsonProperty("CategoryId")
	private Integer categoryId;
	@JsonProperty("CostCenterId")
	private Integer costCenterId;
	@JsonProperty("ProjectId")
	private Integer projectId;
	@JsonProperty("ReportingManagerId")
	private Integer reportingManagerId;
	@JsonProperty("IsBillable")
	private Boolean isBillable;
	@JsonProperty("RateType")
	private String rateType;
	@JsonProperty("IsActive")
	private Boolean isActive;
	@JsonProperty("IsDeleted")
	private Boolean isDeleted;
	@JsonProperty("EmployeeStatusId")
	private Integer employeeStatusId;
	@JsonProperty("IsConfirmed")
	private Boolean isConfirmed;
	@JsonProperty("CreatedDate")
	private LocalDateTime createdDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public Integer getEmployeeCodeNumber() {
		return employeeCodeNumber;
	}

	public void setEmployeeCodeNumber(Integer employeeCodeNumber) {
		this.employeeCodeNumber = employeeCodeNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDateTime getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDateTime dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public LocalDateTime getDateOfResigning() {
		return dateOfResigning;
	}

	public void setDateOfResigning(LocalDateTime dateOfResigning) {
		this.dateOfResigning = dateOfResigning;
	}

	public LocalDateTime getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDateTime releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(Integer costCenterId) {
		this.costCenterId = costCenterId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getReportingManagerId() {
		return reportingManagerId;
	}

	public void setReportingManagerId(Integer reportingManagerId) {
		this.reportingManagerId = reportingManagerId;
	}

	public Boolean getIsBillable() {
		return isBillable;
	}

	public void setIsBillable(Boolean isBillable) {
		this.isBillable = isBillable;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getEmployeeStatusId() {
		return employeeStatusId;
	}

	public void setEmployeeStatusId(Integer employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
	}

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

}