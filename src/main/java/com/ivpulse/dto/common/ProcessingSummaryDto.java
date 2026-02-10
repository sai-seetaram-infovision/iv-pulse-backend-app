package com.ivpulse.dto.common;

public class ProcessingSummaryDto {
	private int processed;
	private int failed;
	private long millis;

	public int getProcessed() {
		return processed;
	}

	public void setProcessed(int processed) {
		this.processed = processed;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public long getMillis() {
		return millis;
	}

	public void setMillis(long millis) {
		this.millis = millis;
	}

	public ProcessingSummaryDto(int processed, int failed, long millis) {
		super();
		this.processed = processed;
		this.failed = failed;
		this.millis = millis;
	}

}
