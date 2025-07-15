package com.pesupal.server.controller;

import com.pesupal.server.dto.request.AddSubscriptionDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.subscription.SubscriptionPlan;
import com.pesupal.server.service.interfaces.SubscriptionPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subscription-plan")
public class SubscriptionPlanController extends CurrentValueRetriever {

    private final SubscriptionPlanService subscriptionPlanService;

    @PostMapping
    public ResponseEntity<ApiResponseDto> addSubscriptionPlan(@RequestBody AddSubscriptionDto addSubscriptionDto) {

        SubscriptionPlan subscriptionPlan = subscriptionPlanService.createNewSubscriptionPlan(addSubscriptionDto, getCurrentUserId());
        return ResponseEntity.ok(new ApiResponseDto("Subscription plan created successfully.", subscriptionPlan));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getAllSubscriptionPlans() {

        List<SubscriptionPlan> subscriptionPlans = subscriptionPlanService.getAllSubscriptionPlans();
        return ResponseEntity.ok(new ApiResponseDto("Subscription plans retrieved successfully.", subscriptionPlans));
    }
}
