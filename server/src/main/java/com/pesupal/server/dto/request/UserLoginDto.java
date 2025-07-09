package com.pesupal.server.dto.request;

import lombok.Data;

@Data
public class UserLoginDto {

    private String email;

    private String password;
}
