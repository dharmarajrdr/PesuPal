package com.pesupal.server.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.dto.response.MediaUploadDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessageDto {

    private Long orgId;

    // Not trusted
    private Long senderId;

    // Using token, the message will be stored in the database
    private String token;

    private String chatId;

    private String message;

    private MediaUploadDto media;
}
