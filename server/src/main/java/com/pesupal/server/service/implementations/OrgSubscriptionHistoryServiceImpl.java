package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.model.org.Org;
import com.pesupal.server.model.org.OrgSubscriptionHistory;
import com.pesupal.server.model.subscription.SubscriptionPlan;
import com.pesupal.server.repository.OrgSubscriptionHistoryRepository;
import com.pesupal.server.service.interfaces.OrgService;
import com.pesupal.server.service.interfaces.OrgSubscriptionHistoryService;
import com.pesupal.server.service.interfaces.SubscriptionPlanService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class OrgSubscriptionHistoryServiceImpl implements OrgSubscriptionHistoryService {

    private final OrgService orgService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SubscriptionPlanService subscriptionPlanService;
    private final OrgSubscriptionHistoryRepository orgSubscriptionHistoryRepository;

    /**
     * Checks if an organization is active based on its subscription history.
     *
     * @param orgId
     * @return
     */
    @Override
    public Boolean isOrgActive(Long orgId) {

        String key = "org:subscription:active:" + orgId;
        Boolean active = (Boolean) redisTemplate.opsForValue().get(key);
        Duration TTL = Duration.ofMinutes(StaticConfig.MAXIMUM_TTL_MINUTES);

        if (active == null) {
            LocalDateTime latestSubscriptionEndDate = getLatestSubscriptionEndDate(orgId);
            if (latestSubscriptionEndDate == null) {
                active = false; // No history means no subscription, hence not active
            } else {
                LocalDateTime currentTime = LocalDateTime.now();
                long minutesLeftForSubscription = Duration.between(currentTime, latestSubscriptionEndDate).toMinutes();
                active = minutesLeftForSubscription > 0; // Active if subscription end date is in the future
                TTL = Duration.ofMinutes(Math.min(minutesLeftForSubscription, StaticConfig.MAXIMUM_TTL_MINUTES)); // Set TTL to the remaining minutes or 30 minutes, whichever is smaller
            }
            redisTemplate.opsForValue().set(key, active, TTL);
        }

        return active;
    }

    /**
     * Retrieves the latest subscription end date for an organization.
     *
     * @param orgId
     * @return
     */
    @Override
    public LocalDateTime getLatestSubscriptionEndDate(Long orgId) {

        return orgSubscriptionHistoryRepository.findLatestEndDateByOrgId(orgId);
    }

    /**
     * Adds a new subscription for an organization.
     *
     * @param code
     */
    @Override
    public OrgSubscriptionHistory addSubscription(Long orgId, String code) {

        Org org = orgService.getOrgById(orgId);

        SubscriptionPlan subscriptionPlan = subscriptionPlanService.getSubscriptionByCode(code);

        if (!subscriptionPlan.isActive()) {
            throw new ActionProhibitedException("The subscription plan with code '" + code + "' is not active. Please choose an active plan.");
        }

        LocalDateTime latestSubscriptionEndDate = getLatestSubscriptionEndDate(orgId);
        if (latestSubscriptionEndDate == null) {
            latestSubscriptionEndDate = LocalDateTime.now();
        }
        OrgSubscriptionHistory orgSubscriptionHistory = new OrgSubscriptionHistory();
        orgSubscriptionHistory.setOrg(org);
        orgSubscriptionHistory.setSubscriptionPlan(subscriptionPlan);
        orgSubscriptionHistory.setStartDate(latestSubscriptionEndDate);
        orgSubscriptionHistory.setEndDate(latestSubscriptionEndDate.plusDays(subscriptionPlan.getNumberOfDays()));
        return orgSubscriptionHistoryRepository.save(orgSubscriptionHistory);
    }
}
