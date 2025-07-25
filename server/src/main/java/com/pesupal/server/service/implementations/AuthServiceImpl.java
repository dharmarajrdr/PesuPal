package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.UserLoginDto;
import com.pesupal.server.security.CustomUserDetails;
import com.pesupal.server.security.JwtUtil;
import com.pesupal.server.service.interfaces.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    /**
     * Handles user login by authenticating the provided credentials.
     *
     * @param userLoginDto
     * @return String - JWT token if authentication is successful
     */
    @Override
    public String login(UserLoginDto userLoginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails);
    }

    /**
     * Generates a JWT token with additional claims for organization context.
     *
     * @param email
     * @param orgMemberId
     * @return
     */
    @Override
    public String generateTokenWithOrgContext(String email, Long orgMemberId) {
        
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("userId", userDetails.getUserPublicId());
        additionalClaims.put("orgMemberId", orgMemberId);

        return jwtUtil.generateToken(userDetails.getUsername(), additionalClaims);
    }

}