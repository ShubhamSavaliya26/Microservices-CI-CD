package com.example.product_service.config;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class UnleashConfiguration {

    @Value("${unleash.api-url}")
    private String unleashApiUrl;

    @Value("${unleash.api-token}")
    private String unleashApiToken;

    @Value("${unleash.environment:development}")
    private String unleashEnvironment;

    @Bean
    public Unleash unleash() {

        UnleashConfig config = UnleashConfig.builder()
                .appName("product-service")
                .instanceId(UUID.randomUUID().toString())
                .unleashAPI(unleashApiUrl)
                .customHttpHeader("Authorization", unleashApiToken)
                .environment(unleashEnvironment)
                .build();

        return new DefaultUnleash(config);
    }
}