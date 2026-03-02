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
public class OnboardingDto {

	@JsonProperty("EmployeeCode")
	private String employeeCode;

	@JsonProperty("LastName")
	private String lastName;

	@JsonProperty("FirstName")
	private String firstName;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("ClientAccess")
	private String clientAccess;

	// "Y"/"N" as-is; keep as String if upstream sends exactly "Y"/"N"
	@JsonProperty("Active")
	private String active;

	@JsonProperty("BGVStatus")
	private String bgvStatus;

	@JsonProperty("Comments")
	private String comments;

	@JsonProperty("LastModifiedDate")
	private LocalDateTime lastModifiedDate;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientAccess() {
		return clientAccess;
	}

	public void setClientAccess(String clientAccess) {
		this.clientAccess = clientAccess;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getBgvStatus() {
		return bgvStatus;
	}

	public void setBgvStatus(String bgvStatus) {
		this.bgvStatus = bgvStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	
}