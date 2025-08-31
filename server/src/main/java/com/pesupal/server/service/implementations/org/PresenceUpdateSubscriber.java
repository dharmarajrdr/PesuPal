package com.pesupal.server.service.implementations.org;

import com.pesupal.server.dto.response.org.UserPresenceUpdateDto;
import com.pesupal.server.model.user.OrgMember;
import com.pesupal.server.repository.org.OrgMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PresenceUpdateSubscriber {

    private final SimpMessagingTemplate messagingTemplate;
    private final OrgMemberRepository orgMemberRepository;

    public void onStatusUpdate(UserPresenceUpdateDto message) {

        System.out.println(message.toString());
        OrgMember orgMember = orgMemberRepository.findByPublicId(message.getOrgMemberId()).orElse(null);
        if (orgMember != null) {
            messagingTemplate.convertAndSend("/topic/presence/" + message.getOrgId(), message);
            orgMember.setStatus(message.getMemberStatus());
            orgMemberRepository.save(orgMember);
        }
    }
}