package com.pesupal.server.dto.request;

import lombok.Data;

@Data
public class ChatMessageDto {

    private Long receiverId;

    private String message;
}
