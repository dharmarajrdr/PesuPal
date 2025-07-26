package com.pesupal.server.service.implementations;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.dto.request.CreateUserDto;
import com.pesupal.server.dto.request.EmailNotificationRequestDto;
import com.pesupal.server.dto.response.UserLoginCheckDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.user.User;
import com.pesupal.server.model.user.UserOnboarding;
import com.pesupal.server.repository.UserRepository;
import com.pesupal.server.service.interfaces.UserOnboardingService;
import com.pesupal.server.service.interfaces.UserService;
import com.pesupal.server.strategies.notification.EmailNotification;
import com.pesupal.server.strategies.notification_template.SignupConfirmationTemplate;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailNotification emailNotification;
    private final UserOnboardingService userOnboardingService;

    /**
     * Gets a user by their ID.
     *
     * @param ownerId
     * @return
     */
    @Override
    public User getUserById(Long ownerId) {

        return userRepository.findById(ownerId).orElseThrow(() -> new DataNotFoundException("User with id " + ownerId + " not found"));
    }

    /**
     * Creates a new user.
     *
     * @param createUserDto
     * @return
     */
    @Override
    @Transactional
    public void createUser(CreateUserDto createUserDto) throws Exception {

        User user = createUserDto.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));   // Encode the password before saving
        user = userRepository.save(user);
        UserOnboarding userOnboarding = userOnboardingService.initiateOnboarding(user);
        EmailNotificationRequestDto<SignupConfirmationTemplate> emailNotificationRequestDto = new EmailNotificationRequestDto<>();
        emailNotificationRequestDto.setRecipientEmail(user.getEmail());
        emailNotificationRequestDto.setTemplate(new SignupConfirmationTemplate(StaticConfig.SERVER_DOMAIN + "/api/v1/user/onboarding/email-verification/" + userOnboarding.getId()));
        // emailNotification.sendNotification(emailNotificationRequestDto);
    }

    /**
     * Retrieves a user by their username. Used for login checks.
     *
     * @param email
     * @return UserLoginCheckDto
     */
    @Override
    public Optional<UserLoginCheckDto> getUserLoginCheckByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserLoginCheckDto::fromUser);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User with email '" + email + "' not found"));
    }

    /**
     * @param userPublicId
     * @return
     */
    @Override
    public User getUserByPublicId(String userPublicId) {

        return userRepository.findByPublicId(userPublicId).orElseThrow(() -> new DataNotFoundException("User with ID '" + userPublicId + "' not found"));
    }

}
