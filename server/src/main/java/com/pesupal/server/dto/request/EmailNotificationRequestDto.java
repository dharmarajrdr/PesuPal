package com.pesupal.server.dto.request;

import lombok.Data;

@Data
public class EmailNotificationRequestDto<T extends EmailTemplate> extends NotificationRequestDto {

    private String recipientEmail;

    private T template;
}
