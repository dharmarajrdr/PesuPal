package com.pesupal.server.dto.request.chat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RescheduleMessageDto {

    private LocalDateTime scheduleAt;
}
