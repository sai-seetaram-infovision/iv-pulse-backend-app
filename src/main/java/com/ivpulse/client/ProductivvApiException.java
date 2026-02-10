package com.ivpulse.client;

public class ProductivvApiException extends RuntimeException {
    private final int status;
    public ProductivvApiException(String message, int status) {
        super(message);
        this.status = status;
    }
    public int getStatus() { return status; }
}
