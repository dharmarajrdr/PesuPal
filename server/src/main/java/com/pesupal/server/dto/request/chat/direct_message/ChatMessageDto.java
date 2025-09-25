package com.pesupal.server.dto.request.chat.direct_message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.MediaUploadDto;
import com.pesupal.server.model.chat.MessageStatus;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessageDto<T> {

    // Not for receiving data, just to broadcast
    private String senderId;

    // Using token, the message will be stored in the database
    private String token;

    private String chatId;

    private String message;

    private MediaUploadDto media;

    private MessageStatus messageStatus = MessageStatus.SENT;

    private LocalDateTime scheduleAt;

    // Either `GroupChatMessage` or `DirectMessage`
    private T messageDto;
}
