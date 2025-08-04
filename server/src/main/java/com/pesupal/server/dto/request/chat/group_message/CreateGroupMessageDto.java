package com.pesupal.server.dto.request.chat.group_message;

import com.pesupal.server.model.group.GroupChatMessage;
import lombok.Data;

@Data
public class CreateGroupMessageDto {

    private String groupId;

    private String message;

    public GroupChatMessage toGroupChatMessage() {
        GroupChatMessage groupChatMessage = new GroupChatMessage();
        groupChatMessage.setMessage(message);
        return groupChatMessage;
    }

}
