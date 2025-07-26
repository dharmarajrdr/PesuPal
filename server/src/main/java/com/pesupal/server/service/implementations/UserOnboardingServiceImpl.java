package com.pesupal.server.service.implementations;

import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.user.User;
import com.pesupal.server.model.user.UserOnboarding;
import com.pesupal.server.repository.UserOnboardingRepository;
import com.pesupal.server.service.interfaces.UserOnboardingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserOnboardingServiceImpl implements UserOnboardingService {

    private final UserOnboardingRepository userOnboardingRepository;

    /**
     * Retrieves the UserOnboarding entity by its invitation ID.
     *
     * @param invitationId
     * @return
     */
    private UserOnboarding getUserOnboardingByInvitationId(UUID invitationId) {

        return userOnboardingRepository.findById(invitationId).orElseThrow(() -> new IllegalArgumentException("User onboarding not found for invitation ID: " + invitationId));
    }

    /**
     * Retrieves the UserOnboarding entity by the associated user.
     *
     * @param user
     * @return
     */
    private UserOnboarding getUserOnboardingByUser(User user) {

        return userOnboardingRepository.findByUser(user).orElseThrow(() -> new DataNotFoundException("User onboarding not found for user: " + user.getEmail()));
    }

    /**
     * Marks the email verification as done for the user onboarding associated with the given invitation ID.
     *
     * @param invitationId
     */
    @Override
    public void emailVerification(UUID invitationId) {

        UserOnboarding userOnboarding = getUserOnboardingByInvitationId(invitationId);
        if (userOnboarding.isEmailVerificationDone()) {
            throw new ActionProhibitedException("Email verification has already been done for this user onboarding.");
        }
        userOnboarding.setEmailVerificationDone(true);
        userOnboardingRepository.save(userOnboarding);
    }

    /**
     * Initiates the onboarding process for a user.
     *
     * @param user
     * @return
     */
    @Override
    public UserOnboarding initiateOnboarding(User user) {

        if (userOnboardingRepository.existsByUserId(user.getId())) {
            throw new ActionProhibitedException("User is already onboarded.");
        }

        UserOnboarding userOnboarding = new UserOnboarding();
        userOnboarding.setUser(user);
        return userOnboardingRepository.save(userOnboarding);
    }

    /**
     * Checks if the user has completed the onboarding verification.
     *
     * @param user
     */
    @Override
    public void hasDoneOnboardingVerification(User user) {

        UserOnboarding userOnboarding = getUserOnboardingByUser(user);
        if (!userOnboarding.isEmailVerificationDone()) {
            throw new DataNotFoundException("User onboarding is pending. Please complete the email verification process.");
        }
        if (!userOnboarding.isPhoneVerificationDone()) {
            throw new DataNotFoundException("User onboarding is pending. Please complete the phone verification process.");
        }
    }
}
