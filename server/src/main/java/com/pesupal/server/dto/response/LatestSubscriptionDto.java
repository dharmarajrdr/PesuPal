package com.pesupal.server.dto.response;

import com.pesupal.server.model.org.OrgSubscriptionHistory;
import com.pesupal.server.model.subscription.SubscriptionPlan;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class LatestSubscriptionDto {

    private String planName;

    private LocalDateTime expiryDate;

    private String status;

    public static LatestSubscriptionDto fromOrgSubscriptionHistory(OrgSubscriptionHistory orgSubscriptionHistory) {

        SubscriptionPlan subscriptionPlan = orgSubscriptionHistory.getSubscriptionPlan();
        LatestSubscriptionDto dto = new LatestSubscriptionDto();
        dto.setPlanName(subscriptionPlan.getCode());
        dto.setExpiryDate(orgSubscriptionHistory.getEndDate());
        long daysLeftToExpire = Duration.between(LocalDateTime.now(), orgSubscriptionHistory.getEndDate()).toDays();
        if (daysLeftToExpire < 0) {
            dto.setStatus("Inactive");
        } else if (daysLeftToExpire <= 7) {
            dto.setStatus("Expiring Soon");
        } else {
            dto.setStatus("Active");
        }
        return dto;
    }
}
