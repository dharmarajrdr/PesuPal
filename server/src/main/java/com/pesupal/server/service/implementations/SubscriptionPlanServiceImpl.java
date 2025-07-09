package com.pesupal.server.service.implementations;

import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.subscription.SubscriptionPlan;
import com.pesupal.server.repository.SubscriptionPlanRepository;
import com.pesupal.server.service.interfaces.SubscriptionPlanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    /**
     * Retrieves a subscription plan by its code.
     *
     * @param code
     * @return
     */
    @Override
    public SubscriptionPlan getSubscriptionByCode(String code) {

        return subscriptionPlanRepository.findByCode(code).orElseThrow(() -> new DataNotFoundException("Subscription plan with code '" + code + "' not found"));
    }
}
