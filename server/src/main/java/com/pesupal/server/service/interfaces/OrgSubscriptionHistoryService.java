package com.pesupal.server.service.interfaces;

import com.pesupal.server.model.org.OrgSubscriptionHistory;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrgSubscriptionHistoryService {

    Boolean isOrgActive(Long orgId);

    LocalDateTime getLatestSubscriptionEndDate(Long orgId);

    Optional<OrgSubscriptionHistory> getLatestSubscription(Long orgId);

    OrgSubscriptionHistory addSubscription(Long orgId, String code);
}
