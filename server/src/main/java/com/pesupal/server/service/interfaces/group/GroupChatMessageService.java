package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.CreateGroupMessageDto;
import com.pesupal.server.dto.response.group.GroupMessageDto;
import com.pesupal.server.model.group.GroupChatMessage;

public interface GroupChatMessageService {

    GroupMessageDto postMessageInGroup(CreateGroupMessageDto createGroupMessageDto, Long userId, Long orgId);

    GroupChatMessage getGroupChatMessageById(Long messageId);

    void deleteGroupMessage(Long messageId, Long userId, Long orgId);
}
