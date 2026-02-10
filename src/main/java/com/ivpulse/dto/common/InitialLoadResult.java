package com.ivpulse.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitialLoadResult {
	private int clients;
	private int engagements;
	private int roles;
	private int rates;
	private int resources;
	private int engagementResources;
	private int hiring;
	private int onboarding;
	private int billingSnapshots;

	private int totalWritten;
	private int errors;
	private long millis;
	
	
	public int getClients() {
		return clients;
	}
	public void setClients(int clients) {
		this.clients = clients;
	}
	public int getEngagements() {
		return engagements;
	}
	public void setEngagements(int engagements) {
		this.engagements = engagements;
	}
	public int getRoles() {
		return roles;
	}
	public void setRoles(int roles) {
		this.roles = roles;
	}
	public int getRates() {
		return rates;
	}
	public void setRates(int rates) {
		this.rates = rates;
	}
	public int getResources() {
		return resources;
	}
	public void setResources(int resources) {
		this.resources = resources;
	}
	public int getEngagementResources() {
		return engagementResources;
	}
	public void setEngagementResources(int engagementResources) {
		this.engagementResources = engagementResources;
	}
	public int getHiring() {
		return hiring;
	}
	public void setHiring(int hiring) {
		this.hiring = hiring;
	}
	public int getOnboarding() {
		return onboarding;
	}
	public void setOnboarding(int onboarding) {
		this.onboarding = onboarding;
	}
	public int getBillingSnapshots() {
		return billingSnapshots;
	}
	public void setBillingSnapshots(int billingSnapshots) {
		this.billingSnapshots = billingSnapshots;
	}
	public int getTotalWritten() {
		return totalWritten;
	}
	public void setTotalWritten(int totalWritten) {
		this.totalWritten = totalWritten;
	}
	public int getErrors() {
		return errors;
	}
	public void setErrors(int errors) {
		this.errors = errors;
	}
	public long getMillis() {
		return millis;
	}
	public void setMillis(long millis) {
		this.millis = millis;
	}
	
	
	
}
