package com.example.order_service.config;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnleashConfiguration {

    @Value("${unleash.api-url}")
    private String unleashApiUrl;

    @Value("${unleash.api-token}")
    private String unleashApiToken;

    @Bean
    public Unleash unleash() {
        UnleashConfig config = UnleashConfig.builder()
                .appName("order-service")
                .instanceId("order-service-instance")
                .unleashAPI(unleashApiUrl)
                .customHttpHeader("Authorization", unleashApiToken)
                .build();

        return new DefaultUnleash(config);
    }
}