package com.pesupal.server.dto.request;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatMessageDto {
    
    private Long orgId;

    private Long senderId;

    private Long receiverId;

    private String message;

    private LocalDateTime createdAt;
}
