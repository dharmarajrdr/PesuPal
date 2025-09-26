package com.pesupal.server.dto.request.chat.group_message;

import lombok.Data;

@Data
public class CreatePinGroupChatMessageDto {

    private String groupId;

    private Integer orderIndex;
}
