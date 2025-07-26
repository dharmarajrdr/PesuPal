package com.pesupal.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetGroupConversationDto {

    private String groupPublicId;

    private Long pivotMessageId;

    private Integer page;

    private Integer size;
}
