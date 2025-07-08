package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateUserDto;
import com.pesupal.server.model.user.User;

public interface UserService {

    User getUserById(Long ownerId);

    User createUser(CreateUserDto createUserDto);
}
