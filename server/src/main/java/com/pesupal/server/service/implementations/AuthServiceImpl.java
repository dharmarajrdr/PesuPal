package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.request.UserLoginDto;
import com.pesupal.server.security.JwtUtil;
import com.pesupal.server.service.interfaces.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Handles user login by authenticating the provided credentials.
     *
     * @param userLoginDto
     * @return String - JWT token if authentication is successful
     */
    @Override
    public String login(UserLoginDto userLoginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails);
    }
}
