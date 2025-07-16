package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.AddSubscriptionDto;
import com.pesupal.server.model.subscription.SubscriptionPlan;

import java.util.List;

public interface SubscriptionPlanService {

    SubscriptionPlan getSubscriptionByCode(String code);

    SubscriptionPlan createNewSubscriptionPlan(AddSubscriptionDto addSubscriptionDto, Long userId);

    SubscriptionPlan getSubscriptionPlanById(Long subscriptionPlanId);

    List<SubscriptionPlan> getAllSubscriptionPlans();
}
