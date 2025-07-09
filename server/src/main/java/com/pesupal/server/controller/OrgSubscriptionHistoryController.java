package com.pesupal.server.controller;

import com.pesupal.server.config.RequestContext;
import com.pesupal.server.dto.response.ApiResponseDto;
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
public class OrgSubscriptionHistoryController {

    private final OrgSubscriptionHistoryService orgSubscriptionHistoryService;

    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponseDto> addSubscription(@RequestBody Map<String, String> map) {

        Long orgId = RequestContext.getLong("X-ORG-ID");
        OrgSubscriptionHistory orgSubscriptionHistory = orgSubscriptionHistoryService.addSubscription(orgId, map.get("code"));
        return ResponseEntity.ok().body(new ApiResponseDto("Subscription added successfully", orgSubscriptionHistory));
    }
}
