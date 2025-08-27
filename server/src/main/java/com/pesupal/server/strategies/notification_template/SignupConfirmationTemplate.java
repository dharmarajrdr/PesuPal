package com.pesupal.server.strategies.notification_template;

import com.pesupal.server.dto.request.EmailTemplate;

public class SignupConfirmationTemplate extends EmailTemplate {

    public SignupConfirmationTemplate(String confirmationLink) {
        super("Signup Confirmation!", """
                Thank you for signing up with us!<br>
                Please click the link below to confirm your email address.<br>
                <a href="%s">Confirm Email</a><br>
                If you did not sign up, please ignore this email.""".formatted(confirmationLink));
    }
}
