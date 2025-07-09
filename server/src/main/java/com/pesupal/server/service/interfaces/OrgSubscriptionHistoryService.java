package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.AddSubscriptionDto;
import com.pesupal.server.model.org.OrgSubscriptionHistory;

import java.time.LocalDateTime;

public interface OrgSubscriptionHistoryService {

    public Boolean isOrgActive(Long orgId);

    LocalDateTime getLatestSubscriptionEndDate(Long orgId);

    public OrgSubscriptionHistory addSubscription(Long orgId, AddSubscriptionDto addSubscriptionDto);
}
