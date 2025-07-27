package com.pesupal.server.dto.request.group;

import lombok.Data;

@Data
public class CreatePinGroupChatMessageDto {

    private String groupId;

    private Integer orderIndex;
}
