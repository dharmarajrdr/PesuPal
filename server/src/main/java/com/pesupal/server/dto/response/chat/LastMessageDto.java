package com.pesupal.server.dto.response.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.MessageType;
import com.pesupal.server.enums.ReadReceipt;
import com.pesupal.server.model.chat.MessageStatus;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LastMessageDto {

    private String sender;

    private String message;

    private boolean media;

    private String createdAt;

    private ReadReceipt readReceipt;

    private MessageStatus messageStatus;

    private MessageType messageType;
}
