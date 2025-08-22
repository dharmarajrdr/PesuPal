package com.pesupal.server.strategies.notification;

import com.pesupal.server.dto.request.EmailNotificationRequestDto;
import com.pesupal.server.dto.request.EmailTemplate;
import com.pesupal.server.dto.response.EmailNotificationResponseDto;
import com.pesupal.server.service.interfaces.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailNotification implements NotificationService<EmailNotificationRequestDto<EmailTemplate>, EmailNotificationResponseDto> {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String FROM_EMAIL;

    /**
     * Sends an email notification based on the provided request DTO.
     *
     * @param notificationDto
     * @return
     */
    @Override
    public EmailNotificationResponseDto sendNotification(EmailNotificationRequestDto notificationDto) throws MessagingException {

        EmailTemplate emailTemplate = notificationDto.getTemplate();

        String recepientEmail = notificationDto.getRecipientEmail();
        String subject = emailTemplate.getSubject();
        String body = emailTemplate.getBody();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(FROM_EMAIL);
        helper.setTo(recepientEmail);
        helper.setSubject(subject);
        helper.setText(body, true);

        javaMailSender.send(mimeMessage);

        return new EmailNotificationResponseDto("Email sent successfully to " + recepientEmail);
    }
}
