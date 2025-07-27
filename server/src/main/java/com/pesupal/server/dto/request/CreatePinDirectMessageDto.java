package com.pesupal.server.dto.request;

import lombok.Data;

@Data
public class CreatePinDirectMessageDto {

    private String chatId;

    private Integer orderIndex;
}
