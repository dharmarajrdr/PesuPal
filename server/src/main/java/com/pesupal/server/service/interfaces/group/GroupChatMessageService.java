package com.pesupal.server.service.interfaces.group;

import com.pesupal.server.dto.request.group.CreateGroupMessageDto;
import com.pesupal.server.dto.response.group.GroupMessageDto;

public interface GroupChatMessageService {

    GroupMessageDto postMessageInGroup(CreateGroupMessageDto createGroupMessageDto, Long userId, Long orgId);
}
