package com.pesupal.server.dto.request;

import com.pesupal.server.model.user.User;
import lombok.Data;

@Data
public class CreateUserDto {

    private String email;

    private String phone;

    private String password;

    public User toUser() {

        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        return user;
    }
}
