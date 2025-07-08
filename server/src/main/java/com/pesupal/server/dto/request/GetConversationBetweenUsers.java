package com.pesupal.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetConversationBetweenUsers {

    private Long userId1;

    private Long userId2;

    private Long orgId;

    private Integer page;

    private Integer size;
}
