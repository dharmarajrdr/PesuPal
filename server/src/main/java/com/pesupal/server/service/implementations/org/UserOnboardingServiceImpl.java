package com.pesupal.server.service.implementations.org;

import com.pesupal.server.exceptions.ActionProhibitedException;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.user.User;
import com.pesupal.server.model.user.UserOnboarding;
import com.pesupal.server.repository.org.UserOnboardingRepository;
import com.pesupal.server.service.interfaces.UserService;
import com.pesupal.server.service.interfaces.org.OrgMemberService;
import com.pesupal.server.service.interfaces.org.UserOnboardingService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserOnboardingServiceImpl implements UserOnboardingService {

    private final UserService userService;
    private final OrgMemberService orgMemberService;
    private final UserOnboardingRepository userOnboardingRepository;

    public UserOnboardingServiceImpl(@Lazy UserService userService, UserOnboardingRepository userOnboardingRepository, @Lazy OrgMemberService orgMemberService) {
        this.userService = userService;
        this.orgMemberService = orgMemberService;
        this.userOnboardingRepository = userOnboardingRepository;
    }

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

        orgMemberService.joinInAllInvitedOrgs(userOnboarding.getUser());
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
            throw new ActionProhibitedException("User onboarding is pending. Please complete the email verification process.");
        }
        if (!userOnboarding.isPhoneVerificationDone()) {
            throw new ActionProhibitedException("User onboarding is pending. Please complete the phone verification process.");
        }
    }

    /**
     * Checks if the user with the given user ID has completed the onboarding verification.
     *
     * @param userId
     */
    @Override
    public void hasDoneOnboardingVerification(String userId) {

        User user = userService.getUserByPublicId(userId);
        hasDoneOnboardingVerification(user);
    }
}
