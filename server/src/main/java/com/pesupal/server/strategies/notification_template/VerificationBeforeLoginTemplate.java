package com.pesupal.server.strategies.notification_template;

import com.pesupal.server.dto.request.EmailTemplate;

public class VerificationBeforeLoginTemplate extends EmailTemplate {

    public VerificationBeforeLoginTemplate(Long otp) {
        super("Verify your email!", """
                Please use the following OTP to verify your email. <br />
                OTP : <strong>%s</strong><br />
                If you did not request this, please ignore this email.""".formatted(otp));

    }
}
