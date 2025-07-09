package com.pesupal.server.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pesupal.server.enums.ReadReceipt;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LastMessageDto {

    private String sender;

    private String content;

    private boolean includedMedia;

    private LocalDateTime createdAt;

    private ReadReceipt readReceipt;
}
