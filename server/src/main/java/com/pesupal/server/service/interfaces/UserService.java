package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateUserDto;
import com.pesupal.server.dto.response.UserLoginCheckDto;
import com.pesupal.server.model.user.User;

import java.util.Optional;

public interface UserService {

    User getUserById(Long userId);

    void createUser(CreateUserDto createUserDto) throws Exception;

    Optional<UserLoginCheckDto> getUserLoginCheckByEmail(String email);

    User getUserByEmail(String email);
}
