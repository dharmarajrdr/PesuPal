package com.pesupal.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/api/v1/user/**").permitAll()    // Allow unauthenticated access to user endpoints
                        .anyRequest().authenticated()   // All other requests require authentication
        );

        httpSecurity.formLogin(form -> form.permitAll());   // No authentication required for Login form

        return httpSecurity.build();
    }
}
