package com.ivpulse.dto.dashboard;

import java.time.OffsetDateTime;

public class ApiEnvelope<T> {

    private T data;
    private Meta meta;

    // No-args constructor
    public ApiEnvelope() {
    }

    // All-args constructor
    public ApiEnvelope(T data, Meta meta) {
        this.data = data;
        this.meta = meta;
    }

    // Getters and Setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    // Static factory method
    public static <T> ApiEnvelope<T> of(
            T data,
            boolean empty,
            OffsetDateTime lastProcessedAt,
            String message) {

        Meta meta = new Meta(empty, lastProcessedAt, message);
        return new ApiEnvelope<>(data, meta);
    }

    // =======================
    // Inner Meta class
    // =======================
    public static class Meta {

        private boolean empty;
        private OffsetDateTime lastProcessedAt;
        private String message;

        // No-args constructor
        public Meta() {
        }

        // All-args constructor
        public Meta(boolean empty, OffsetDateTime lastProcessedAt, String message) {
            this.empty = empty;
            this.lastProcessedAt = lastProcessedAt;
            this.message = message;
        }

        // Getters and Setters
        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        public OffsetDateTime getLastProcessedAt() {
            return lastProcessedAt;
        }

        public void setLastProcessedAt(OffsetDateTime lastProcessedAt) {
            this.lastProcessedAt = lastProcessedAt;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
