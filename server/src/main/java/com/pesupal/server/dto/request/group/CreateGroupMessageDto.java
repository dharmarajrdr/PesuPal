package com.pesupal.server.dto.request.group;

import com.pesupal.server.model.group.GroupChatMessage;
import lombok.Data;

@Data
public class CreateGroupMessageDto {

    private Long groupId;

    private String message;

    public GroupChatMessage toGroupChatMessage() {
        GroupChatMessage groupChatMessage = new GroupChatMessage();
        groupChatMessage.setMessage(message);
        return groupChatMessage;
    }

}
