package com.pesupal.server.service.interfaces;

import com.stripe.param.PlanCreateParams;

public interface SubscriptionService {

    public String createSubscriptionForProduct(String customerName, String customerEmail, Long productAmount, String productName, PlanCreateParams.Interval interval);
}
