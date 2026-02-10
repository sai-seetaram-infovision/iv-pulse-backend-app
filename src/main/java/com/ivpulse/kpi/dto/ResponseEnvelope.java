
package com.ivpulse.kpi.dto;

import java.time.Instant;

public class ResponseEnvelope<T> {
	public final boolean success;
	public final String message;
	public final Instant timestamp = Instant.now();
	public final T data;

	private ResponseEnvelope(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public static <T> ResponseEnvelope<T> ok(T data, String message) {
		return new ResponseEnvelope<>(true, message, data);
	}

	public static <T> ResponseEnvelope<T> error(String message) {
		return new ResponseEnvelope<>(false, message, null);
	}
}
