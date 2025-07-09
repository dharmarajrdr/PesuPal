package com.pesupal.server.controller;

import com.pesupal.server.dto.request.AddSubscriptionDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.model.subscription.SubscriptionPlan;
import com.pesupal.server.security.SecurityUtil;
import com.pesupal.server.service.interfaces.SubscriptionPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subscription-plan")
public class SubscriptionPlanController {

    private final SecurityUtil securityUtil;
    private final SubscriptionPlanService subscriptionPlanService;

    @PostMapping
    public ResponseEntity<ApiResponseDto> addSubscriptionPlan(@RequestBody AddSubscriptionDto addSubscriptionDto) {

        Long userId = securityUtil.getCurrentUserId();
        SubscriptionPlan subscriptionPlan = subscriptionPlanService.createNewSubscriptionPlan(addSubscriptionDto, userId);
        return ResponseEntity.ok(new ApiResponseDto("Subscription plan created successfully.", subscriptionPlan));
    }
}
