package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.response.org.UserPresenceUpdateDto;
import com.pesupal.server.enums.MemberStatus;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.service.interfaces.org.PresenceService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class PresenceServiceImpl extends CurrentValueRetriever implements PresenceService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final PresenceUpdateSubscriber presenceUpdateSubscriber;

    private static final String ORG_MEMBER_PRESENCE_KEY_PREFIX = "presence:org_member:";

    /**
     * Informs presence of an organization member.
     */
    @Override
    public void informPresence() {

        OrgMember orgMember = getCurrentOrgMember();
        String orgMemberId = orgMember.getPublicId();
        String orgId = orgMember.getOrg().getPublicId();

        String presenceKey = ORG_MEMBER_PRESENCE_KEY_PREFIX + orgId + ":" + orgMemberId;
        boolean isUserAlreadyOnline = redisTemplate.opsForValue().get(presenceKey) != null;

        if (isUserAlreadyOnline) { // Status remains ONLINE, just refresh the expiration
            redisTemplate.expire(presenceKey, Duration.ofSeconds(20));
        } else {    // Status changed from OFFLINE to ONLINE
            redisTemplate.opsForValue().set(presenceKey, "ONLINE");
            redisTemplate.expire(presenceKey, Duration.ofSeconds(20));
            presenceUpdateSubscriber.onStatusUpdate(new UserPresenceUpdateDto(orgId, orgMemberId, MemberStatus.AVAILABLE));
        }
    }

    /**
     * Broadcasts the status of organization members to all available members.
     */
    public void broadcastOrgMemberStatusToAvailableMembers() {


    }
}
