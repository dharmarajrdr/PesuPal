package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.CreateUserDto;
import com.pesupal.server.model.user.User;

public interface UserService {

    User getUserById(Long userId);

    void createUser(CreateUserDto createUserDto) throws Exception;

    User getUserByEmail(String email);

    User getUserByPublicId(String userPublicId);
}
