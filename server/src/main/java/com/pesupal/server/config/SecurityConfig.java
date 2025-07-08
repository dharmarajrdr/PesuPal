package com.pesupal.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize ->
                authorize.requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()    // No authentication required to create a new user
                        .anyRequest().authenticated()   // All other requests require authentication
        );

        httpSecurity.csrf(csrf -> csrf.disable());  // Disabling CSRF protection to allow POST requests without CSRF tokens

        httpSecurity.formLogin(form ->
                form.permitAll().defaultSuccessUrl("/login-successful")
        );   // Configuring "No authentication required" for Login form

        return httpSecurity.build();
    }

    /**
     * Login form authentication
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails user1 = User.withUsername("dharma").password(passwordEncoder.encode("123")).roles("USER").build();
        UserDetails user2 = User.withUsername("mohan").password(passwordEncoder.encode("123")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    /**
     * Password encoder bean for encoding passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
