package com.pesupal.server.dto.request.group;

import lombok.Data;

@Data
public class CreatePinGroupChatMessageDto {

    private Long pinnedGroupId;

    private Integer orderIndex;
}
