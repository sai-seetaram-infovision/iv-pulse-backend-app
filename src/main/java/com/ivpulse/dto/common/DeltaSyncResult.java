package com.ivpulse.dto.common;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeltaSyncResult {
	private LocalDate employeesFrom;
	private LocalDate employeesTo;
	private int employeesWritten;

	private LocalDate tsCurrentFrom;
	private LocalDate tsCurrentTo;
	private int timesheetsCurrentWritten;

	private LocalDate tsLastFrom;
	private LocalDate tsLastTo;
	private int timesheetsLastWritten;

	private int errors;
	private long millis;
	
	public LocalDate getEmployeesFrom() {
		return employeesFrom;
	}
	public void setEmployeesFrom(LocalDate employeesFrom) {
		this.employeesFrom = employeesFrom;
	}
	public LocalDate getEmployeesTo() {
		return employeesTo;
	}
	public void setEmployeesTo(LocalDate employeesTo) {
		this.employeesTo = employeesTo;
	}
	public int getEmployeesWritten() {
		return employeesWritten;
	}
	public void setEmployeesWritten(int employeesWritten) {
		this.employeesWritten = employeesWritten;
	}
	public LocalDate getTsCurrentFrom() {
		return tsCurrentFrom;
	}
	public void setTsCurrentFrom(LocalDate tsCurrentFrom) {
		this.tsCurrentFrom = tsCurrentFrom;
	}
	public LocalDate getTsCurrentTo() {
		return tsCurrentTo;
	}
	public void setTsCurrentTo(LocalDate tsCurrentTo) {
		this.tsCurrentTo = tsCurrentTo;
	}
	public int getTimesheetsCurrentWritten() {
		return timesheetsCurrentWritten;
	}
	public void setTimesheetsCurrentWritten(int timesheetsCurrentWritten) {
		this.timesheetsCurrentWritten = timesheetsCurrentWritten;
	}
	public LocalDate getTsLastFrom() {
		return tsLastFrom;
	}
	public void setTsLastFrom(LocalDate tsLastFrom) {
		this.tsLastFrom = tsLastFrom;
	}
	public LocalDate getTsLastTo() {
		return tsLastTo;
	}
	public void setTsLastTo(LocalDate tsLastTo) {
		this.tsLastTo = tsLastTo;
	}
	public int getTimesheetsLastWritten() {
		return timesheetsLastWritten;
	}
	public void setTimesheetsLastWritten(int timesheetsLastWritten) {
		this.timesheetsLastWritten = timesheetsLastWritten;
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
