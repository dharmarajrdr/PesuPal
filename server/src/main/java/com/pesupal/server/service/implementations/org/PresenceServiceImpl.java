package com.pesupal.server.service.implementations.org;

import com.pesupal.server.enums.MemberStatus;
import com.pesupal.server.helpers.CurrentValueRetriever;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.org.OrgMemberRepository;
import com.pesupal.server.service.interfaces.org.PresenceService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PresenceServiceImpl extends CurrentValueRetriever implements PresenceService {

    private final OrgMemberRepository orgMemberRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Informs presence of an organization member.
     */
    @Override
    public void informPresence() {

        OrgMember orgMember = getCurrentOrgMember();
        orgMember.setStatus(MemberStatus.AVAILABLE);
        orgMemberRepository.save(orgMember);
    }

    /**
     * Broadcasts the status of organization members to all available members.
     */
    public void broadcastOrgMemberStatusToAvailableMembers() {


    }
}
