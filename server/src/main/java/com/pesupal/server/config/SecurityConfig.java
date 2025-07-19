package com.pesupal.server.config;

import com.pesupal.server.security.JwtFilter;
import com.pesupal.server.service.implementations.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(@Lazy JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(Customizer.withDefaults());

        httpSecurity.authorizeHttpRequests(authorize ->
                authorize.requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()    // No authentication required for sign-up
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()    // No authentication required for login
                        .requestMatchers("/api/v1/user/**").permitAll()     // Allow all requests to user endpoints
                        .requestMatchers("/api/v1/media/**").permitAll()    // Allow all requests to media endpoints
                        .requestMatchers("/chat/**").permitAll()  // Allow all requests to the chat endpoint
                        .anyRequest().authenticated()   // All other requests require authentication
        );

        httpSecurity.csrf(csrf -> csrf.disable());  // Disabling CSRF protection to allow POST requests without CSRF tokens

        httpSecurity.sessionManagement(sessionManagementCustomizer -> {
            sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Don't maintain session state
        });

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);    // Adding JWT filter before UsernamePasswordAuthenticationFilter

        // This login form is only for testing purposes and so commenting it out.
        /*
        httpSecurity.formLogin(form ->
                form.permitAll().defaultSuccessUrl("/login-successful")     // Configuring "No authentication required" for Login form
        );
        */

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Login user details service bean
     */
    @Bean
    public UserDetailsService userDetailsService(/* PasswordEncoder passwordEncoder */) {

        // In-memory user details service for testing authentication
        /*
            UserDetails user1 = User.withUsername("dharma").password(passwordEncoder.encode("123")).roles("USER").build();
            UserDetails user2 = User.withUsername("mohan").password(passwordEncoder.encode("123")).roles("ADMIN").build();
            return new InMemoryUserDetailsManager(user1, user2);
         */

        // Fetching user details from the database using a custom service
        return new CustomUserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    /**
     * Authentication manager bean for managing authentication
     *
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager() {

        return new ProviderManager(
                List.of(authenticationProvider())   // We only have one authentication provider
        );
    }

    /**
     * Password encoder bean for encoding passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}

