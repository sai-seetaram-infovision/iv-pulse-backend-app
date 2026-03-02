package com.ivpulse.entity;

import com.ivpulse.entity.enums.EmploymentStatus;
import com.ivpulse.entity.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "resource", indexes = { @Index(name = "idx_resource_email", columnList = "email"),
		@Index(name = "idx_resource_status", columnList = "employment_status"),
		@Index(name = "idx_resource_join_exit", columnList = "joining_date,exit_date") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceEntity {
	@Id
	@Column(name = "resource_id", columnDefinition = "uuid")
	private UUID resourceId;

	@Column(name = "external_employee_id", length = 100)
	private String externalEmployeeId;

	@Column(name = "full_name", length = 250)
	private String fullName;

	@Column(name = "email", length = 250)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(name = "employment_type", length = 20)
	private EmploymentType employmentType; // FTE/Contract

	@Column(name = "base_location", length = 100)
	private String baseLocation;

	@Column(name = "grade_level", length = 100)
	private String gradeLevel;

	@Column(name = "primary_skill", length = 200)
	private String primarySkill;

	@Column(name = "joining_date")
	private LocalDate joiningDate;

	@Column(name = "exit_date")
	private LocalDate exitDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "employment_status", length = 20, nullable = false)
	private EmploymentStatus employmentStatus; // Active/Exited/Maternity

	@Column(name = "source_system", length = 50)
	private String sourceSystem; // PLT/Manual

	@CreationTimestamp
	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	@Column(name = "is_billable")
	private boolean isBillable;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "employee_status_id")
	private int employeeStatusId;

	@Column(name = "project_id")
	private int projectId;

	public boolean isBillable() {
		return isBillable;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getEmployeeStatusId() {
		return employeeStatusId;
	}

	public void setEmployeeStatusId(int employeeStatusId) {
		this.employeeStatusId = employeeStatusId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public void setBillable(boolean isBillable) {
		this.isBillable = isBillable;
	}

	public UUID getResourceId() {
		return resourceId;
	}

	public void setResourceId(UUID resourceId) {
		this.resourceId = resourceId;
	}

	public String getExternalEmployeeId() {
		return externalEmployeeId;
	}

	public void setExternalEmployeeId(String externalEmployeeId) {
		this.externalEmployeeId = externalEmployeeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EmploymentType getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(EmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public String getBaseLocation() {
		return baseLocation;
	}

	public void setBaseLocation(String baseLocation) {
		this.baseLocation = baseLocation;
	}

	public String getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	public LocalDate getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}

	public LocalDate getExitDate() {
		return exitDate;
	}

	public void setExitDate(LocalDate exitDate) {
		this.exitDate = exitDate;
	}

	public EmploymentStatus getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(EmploymentStatus employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
