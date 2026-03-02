
package com.ivpulse.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountsDto {
	private long totalHc;
	private long totalBillableHc;
	private long totalNonBillableHc;
	private long totalEnablingHc;
	private long totalBenchHc;
	private long totalOnNoticeHc;
	private long totalOnMaternityHc;


	public long getTotalHc() {
		return totalHc;
	}

	public void setTotalHc(long totalHc) {
		this.totalHc = totalHc;
	}

	public long getTotalBillableHc() {
		return totalBillableHc;
	}

	public void setTotalBillableHc(long totalBillableHc) {
		this.totalBillableHc = totalBillableHc;
	}

	public long getTotalNonBillableHc() {
		return totalNonBillableHc;
	}

	public void setTotalNonBillableHc(long totalNonBillableHc) {
		this.totalNonBillableHc = totalNonBillableHc;
	}

	public long getTotalEnablingHc() {
		return totalEnablingHc;
	}

	public void setTotalEnablingHc(long totalEnablingHc) {
		this.totalEnablingHc = totalEnablingHc;
	}

	public long getTotalBenchHc() {
		return totalBenchHc;
	}

	public void setTotalBenchHc(long totalBenchHc) {
		this.totalBenchHc = totalBenchHc;
	}

	public long getTotalOnNoticeHc() {
		return totalOnNoticeHc;
	}

	public void setTotalOnNoticeHc(long totalOnNoticeHc) {
		this.totalOnNoticeHc = totalOnNoticeHc;
	}

	public long getTotalOnMaternityHc() {
		return totalOnMaternityHc;
	}

	public void setTotalOnMaternityHc(long totalOnMaternityHc) {
		this.totalOnMaternityHc = totalOnMaternityHc;
	}
}
