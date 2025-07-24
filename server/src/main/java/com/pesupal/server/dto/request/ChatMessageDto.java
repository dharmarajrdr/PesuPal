package com.pesupal.server.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessageDto {

    private Long orgId;

    private Long senderId;

    private String chatId;

    private String message;
}
