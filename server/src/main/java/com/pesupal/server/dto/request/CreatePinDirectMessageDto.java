package com.pesupal.server.dto.request;

import lombok.Data;

@Data
public class CreatePinDirectMessageDto {

    private Long pinnedUserId;

    private Integer orderIndex;
}
