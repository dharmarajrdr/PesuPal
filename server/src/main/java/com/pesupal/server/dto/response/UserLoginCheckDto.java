package com.pesupal.server.dto.response;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.user.User;
import lombok.Data;

@Data
public class UserLoginCheckDto {

    private String email;

    private String password;

    private String role;

    public static UserLoginCheckDto fromUser(User user) {

        UserLoginCheckDto userLoginCheckDto = new UserLoginCheckDto();
        userLoginCheckDto.setEmail(user.getEmail());
        userLoginCheckDto.setPassword(user.getPassword());
        userLoginCheckDto.setRole(Role.USER.name());    // HARDCODED ROLE
        return userLoginCheckDto;
    }
}
