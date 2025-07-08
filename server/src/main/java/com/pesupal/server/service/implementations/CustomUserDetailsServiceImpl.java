package com.pesupal.server.service.implementations;

import com.pesupal.server.dto.response.UserLoginCheckDto;
import com.pesupal.server.service.interfaces.CustomUserDetailsService;
import com.pesupal.server.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * Loads user details by username.
     *
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserLoginCheckDto user = userService.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
        return new User(user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
    }
}
