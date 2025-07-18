package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateUserDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.model.user.User;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponseDto> createUser(@RequestBody CreateUserDto createUserDto) throws Exception {

        User createdUser = userService.createUser(createUserDto);
        return ResponseEntity.ok(new ApiResponseDto("User created successfully.", createdUser));
    }
}
