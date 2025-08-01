package com.pesupal.server.helpers;

import com.pesupal.server.exceptions.SubscriptionNotActiveException;
import com.pesupal.server.service.interfaces.OrgSubscriptionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgSubscriptionManager extends CurrentValueRetriever {

    @Autowired
    private OrgSubscriptionHistoryService orgSubscriptionHistoryService;

    /**
     * Checks if an organization has an active subscription.
     *
     * @throws SubscriptionNotActiveException
     */
    public void checkOrgSubscription() {

        Long orgId = getCurrentOrgId();
        Boolean isActive = orgSubscriptionHistoryService.isOrgActive(orgId);
        if (!isActive.equals(true)) {
            throw new SubscriptionNotActiveException(orgId);
        }
    }
}
