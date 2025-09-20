package com.pesupal.server.config;

import com.pesupal.server.dto.response.org.UserPresenceUpdateDto;
import com.pesupal.server.enums.MemberStatus;
import com.pesupal.server.service.implementations.org.PresenceUpdateSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private PresenceUpdateSubscriber presenceUpdateSubscriber;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        String expiredKey = message.toString();

        if (expiredKey.startsWith("presence:org_member:")) {
            expiredKey = expiredKey.replace("presence:org_member:", "");
            String[] keys = expiredKey.split(":");
            String orgId = keys[0];
            String orgMemberId = keys[1];
            presenceUpdateSubscriber.onStatusUpdate(new UserPresenceUpdateDto(orgId, orgMemberId, MemberStatus.OFFLINE));
        }
    }
}
