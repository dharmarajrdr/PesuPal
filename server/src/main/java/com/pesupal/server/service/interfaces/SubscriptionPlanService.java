package com.pesupal.server.service.interfaces;

import com.pesupal.server.model.subscription.SubscriptionPlan;

public interface SubscriptionPlanService {

    SubscriptionPlan getSubscriptionByCode(String code);
}
