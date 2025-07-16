package com.pesupal.server.controller;

import com.pesupal.server.dto.request.PurchaseSubscriptionDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.org.OrgSubscriptionHistory;
import com.pesupal.server.service.interfaces.OrgSubscriptionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/org-subscription")
public class OrgSubscriptionHistoryController extends CurrentValueRetriever {

    private final OrgSubscriptionHistoryService orgSubscriptionHistoryService;

    @PostMapping("/subscribe")
    // deprecated
    public ResponseEntity<ApiResponseDto> addSubscription(@RequestBody Map<String, String> map) {

        OrgSubscriptionHistory orgSubscriptionHistory = orgSubscriptionHistoryService.addSubscription(getCurrentOrgId(), map.get("code"), null);
        return ResponseEntity.ok().body(new ApiResponseDto("Subscription added successfully", orgSubscriptionHistory));
    }

    @PostMapping("/payment-link")
    public ResponseEntity<ApiResponseDto> generatePaymentLink(@RequestBody PurchaseSubscriptionDto purchaseSubscriptionDto) throws Exception {

        String paymentLink = orgSubscriptionHistoryService.generatePaymentLink(purchaseSubscriptionDto, getCurrentUserId(), getCurrentOrgId());
        return ResponseEntity.ok().body(new ApiResponseDto("Payment link generated successfully", paymentLink));
    }
}
