package com.ivpulse.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductivvProperties {

    /** Base URL like http://27.107.6.21:8082/api/export */
    @Value("${productivv.base-url}")
    private String baseUrl;

    /** Header name e.g., x-api-key */
    @Value("${productivv.api-key-header:x-api-key}")  // default x-api-key
    private String apiKeyHeader;

    /** API key value */
    @Value("${productivv.api-key}")
    private String apiKey;

    // Getters
    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKeyHeader() {
        return apiKeyHeader;
    }

    public String getApiKey() {
        return apiKey;
    }
}
