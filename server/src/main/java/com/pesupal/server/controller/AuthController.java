package com.pesupal.server.controller;

import com.pesupal.server.dto.request.UserLoginDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody UserLoginDto userLoginDto) {

        String token = authService.login(userLoginDto);
        return ResponseEntity.ok().body(new ApiResponseDto("Login successful.", Map.of("token", token)));
    }
}
