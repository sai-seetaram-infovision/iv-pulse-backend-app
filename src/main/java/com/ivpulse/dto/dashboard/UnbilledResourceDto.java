
package com.ivpulse.dto.dashboard;

public class UnbilledResourceDto {
	private String name;
	private String project;
	private String role;
	private long unbilledDays;
	private String reason;

	public UnbilledResourceDto(String name, String project, String role, long unbilledDays, String reason) {
		super();
		this.name = name;
		this.project = project;
		this.role = role;
		this.unbilledDays = unbilledDays;
		this.reason = reason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getUnbilledDays() {
		return unbilledDays;
	}

	public void setUnbilledDays(long unbilledDays) {
		this.unbilledDays = unbilledDays;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
