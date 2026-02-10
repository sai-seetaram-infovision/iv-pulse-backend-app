package com.ivpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivpulse.client.ProductivvProperties;

@Configuration
public class WebClientConfig {

    @Bean(name = "productivvWebClient")
    public WebClient productivvWebClient(ProductivvProperties props, ObjectMapper springMapper) {
        // Ensure JavaTimeModule etc. already registered by Spring
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(c -> {
                c.defaultCodecs().maxInMemorySize(20 * 1024 * 1024);
                c.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(springMapper));
                c.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(springMapper));
            })
            .build();

        return WebClient.builder()
            .baseUrl(props.getBaseUrl())
            .defaultHeader(props.getApiKeyHeader(), props.getApiKey())
            .exchangeStrategies(strategies)
            .build();
    }
}