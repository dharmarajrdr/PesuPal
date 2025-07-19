package com.pesupal.server.controller;

import com.pesupal.server.dto.request.CreateUserDto;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.service.interfaces.UserOnboardingService;
import com.pesupal.server.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserOnboardingService userOnboardingService;

    @PostMapping()
    public ResponseEntity<ApiResponseDto> createUser(@RequestBody CreateUserDto createUserDto) throws Exception {

        userService.createUser(createUserDto);
        return ResponseEntity.ok(new ApiResponseDto("User created successfully."));
    }

    @GetMapping("/onboarding/email-verification/{invitationId}")
    public ResponseEntity<ApiResponseDto> emailVerification(@PathVariable UUID invitationId) {

        userOnboardingService.emailVerification(invitationId);
        return ResponseEntity.ok(new ApiResponseDto("Email verification successful."));
    }
}
