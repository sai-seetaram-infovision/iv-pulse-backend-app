
package com.ivpulse.dto.dashboard;

import java.time.LocalDate;

public class BgvItemDto {
	private String name;
	private String project;
	private String status;
	private LocalDate startedOn;
	private long agingDays;

	public BgvItemDto(String name, String project, String status, LocalDate startedOn, long agingDays) {
		super();
		this.name = name;
		this.project = project;
		this.status = status;
		this.startedOn = startedOn;
		this.agingDays = agingDays;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getStartedOn() {
		return startedOn;
	}

	public void setStartedOn(LocalDate startedOn) {
		this.startedOn = startedOn;
	}

	public long getAgingDays() {
		return agingDays;
	}

	public void setAgingDays(long agingDays) {
		this.agingDays = agingDays;
	}
}
