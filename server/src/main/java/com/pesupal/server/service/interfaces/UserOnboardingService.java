package com.pesupal.server.service.interfaces;

import com.pesupal.server.model.user.User;
import com.pesupal.server.model.user.UserOnboarding;

import java.util.UUID;

public interface UserOnboardingService {

    void emailVerification(UUID invitationId);

    UserOnboarding initiateOnboarding(User user);

    void hasDoneOnboardingVerification(User user);
}
