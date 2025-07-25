package com.pesupal.server.service.implementations;

import com.pesupal.server.enums.Role;
import com.pesupal.server.model.user.User;
import com.pesupal.server.security.CustomUserDetails;
import com.pesupal.server.service.interfaces.CustomUserDetailsService;
import com.pesupal.server.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        User user = userService.getUserByEmail(email);
        return new CustomUserDetails(user.getEmail(), user.getPassword(), user.getPublicId(), null, Collections.singleton(new SimpleGrantedAuthority(Role.USER.name())));
    }

}
