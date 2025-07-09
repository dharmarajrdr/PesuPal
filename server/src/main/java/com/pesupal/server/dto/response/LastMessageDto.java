package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.ReadReceipt;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LastMessageDto {

    private String sender;

    private String message;

    private boolean media;

    private String createdAt;

    private ReadReceipt readReceipt;
}
