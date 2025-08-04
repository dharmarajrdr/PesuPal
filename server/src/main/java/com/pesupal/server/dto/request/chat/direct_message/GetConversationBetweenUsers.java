package com.pesupal.server.dto.request.chat.direct_message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetConversationBetweenUsers {

    private String chatId;

    private Long pivotMessageId;

    private Integer page;

    private Integer size;
}
