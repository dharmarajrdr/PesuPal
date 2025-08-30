package com.pesupal.server.service.interfaces;

import jakarta.mail.MessagingException;

public interface NotificationService<NotificationRequestDto, NotificationResponseDto> {

    NotificationResponseDto sendNotification(NotificationRequestDto notificationDto) throws MessagingException;
}
