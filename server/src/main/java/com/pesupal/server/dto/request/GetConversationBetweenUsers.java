package com.pesupal.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetConversationBetweenUsers {

    private String chatId;

    private Integer page;

    private Integer size;
}
