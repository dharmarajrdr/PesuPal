package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.model.org.OrgSubscriptionHistory;
import com.pesupal.server.repository.OrgSubscriptionHistoryRepository;
import com.pesupal.server.service.interfaces.OrgSubscriptionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrgSubscriptionHistoryServiceImpl implements OrgSubscriptionHistoryService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final OrgSubscriptionHistoryRepository orgSubscriptionHistoryRepository;

    /**
     * Checks if an organization is active based on its subscription history.
     *
     * @param orgId
     * @return
     */
    @Override
    public Boolean isOrgActive(Long orgId) {

        String key = "subscription:org:" + orgId;
        Boolean active = (Boolean) redisTemplate.opsForValue().get(key);
        Duration TTL = Duration.ofMinutes(StaticConfig.MAXIMUM_TTL_MINUTES);

        if (active == null) {
            List<OrgSubscriptionHistory> history = orgSubscriptionHistoryRepository.findByOrgId(orgId);
            if (history.isEmpty()) {
                active = false; // No history means no subscription, hence not active
            } else {
                LocalDateTime currentTime = LocalDateTime.now();
                OrgSubscriptionHistory latestHistory = history.get(history.size() - 1);
                long minutesLeftForSubscription = Duration.between(currentTime, latestHistory.getEndDate()).toMinutes();
                active = minutesLeftForSubscription > 0; // Active if subscription end date is in the future
                TTL = Duration.ofMinutes(Math.min(minutesLeftForSubscription, StaticConfig.MAXIMUM_TTL_MINUTES)); // Set TTL to the remaining minutes or 30 minutes, whichever is smaller
            }
            redisTemplate.opsForValue().set(key, active, TTL);
        }

        return active;
    }
}
