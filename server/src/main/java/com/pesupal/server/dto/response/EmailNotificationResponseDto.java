package com.pesupal.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailNotificationResponseDto extends NotificationResponseDto {

    private String message;
}
