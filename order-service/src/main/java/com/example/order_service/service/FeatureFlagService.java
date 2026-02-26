package com.example.order_service.service;

import io.getunleash.Unleash;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {

    private final Unleash unleash;

    public FeatureFlagService(Unleash unleash) {
        this.unleash = unleash;
    }
    public boolean isFeatureEnabled(String featureName) {
        return unleash.isEnabled(featureName);
    }

    public boolean isOrderNotificationsEnabled() {
        return unleash.isEnabled("order-notifications", false);
    }

    public boolean isBulkDiscountEnabled() {
        return unleash.isEnabled("bulk-order-discount", false);
    }
}
