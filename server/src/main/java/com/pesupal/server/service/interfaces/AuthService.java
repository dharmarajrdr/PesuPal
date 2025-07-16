package com.pesupal.server.service.interfaces;

import com.pesupal.server.dto.request.UserLoginDto;

public interface AuthService {

    String login(UserLoginDto userLoginDto);
}
