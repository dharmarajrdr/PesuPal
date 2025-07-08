package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.CreateUserDto;
import com.pesupal.server.exceptions.DataNotFoundException;
import com.pesupal.server.model.user.User;
import com.pesupal.server.repository.UserRepository;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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
    public User createUser(CreateUserDto createUserDto) {

        User user = createUserDto.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));   // Encode the password before saving
        user = userRepository.save(user);
        user.setPassword(null);
        return user;
    }
}
