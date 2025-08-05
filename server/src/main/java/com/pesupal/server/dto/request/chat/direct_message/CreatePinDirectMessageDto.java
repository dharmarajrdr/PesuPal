package com.pesupal.server.dto.request.chat.direct_message;

import lombok.Data;

@Data
public class CreatePinDirectMessageDto {

    private String chatId;

    private Integer orderIndex;
}
