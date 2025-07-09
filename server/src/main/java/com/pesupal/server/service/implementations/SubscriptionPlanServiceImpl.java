package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.AddSubscriptionDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.exceptions.PermissionDeniedException;
import com.pesupal.server.model.subscription.SubscriptionPlan;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.SubscriptionPlanRepository;
import com.pesupal.server.service.interfaces.SubscriptionPlanService;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final UserService userService;
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

    /**
     * Creates a new subscription plan.
     *
     * @param addSubscriptionDto
     * @param userId
     * @return
     */
    @Override
    public SubscriptionPlan createNewSubscriptionPlan(AddSubscriptionDto addSubscriptionDto, Long userId) {

        User user = userService.getUserById(userId);
        if (!StaticConfig.eligibleToCreateSubscriptionPlan(user.getRole())) {
            throw new PermissionDeniedException("You do not have permission to create a subscription plan.");
        }
        SubscriptionPlan subscriptionPlan = addSubscriptionDto.toSubscriptionPlan();
        subscriptionPlan.setActive(true);
        return subscriptionPlanRepository.save(subscriptionPlan);
    }
}
