package com.pesupal.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pesupal.server.dto.response.ApiResponseDto;
import com.pesupal.server.enums.ResponseStatus;
import com.pesupal.server.service.interfaces.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Processes the incoming HTTP request to check for a JWT token in the Authorization header.
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix

        try {
            String email = jwtUtil.extractEmail(token);

            // Check if the email is not null and the SecurityContext does not already have an authentication
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                if (jwtUtil.validateToken(token, userDetails)) {

                    // After validation, setting the user details in the SecurityContext

                    Claims claims = jwtUtil.withJwtParser().parseClaimsJws(token).getBody();
                    String userId = claims.get("userId", String.class);
                    String orgMemberId = claims.get("orgMemberId", String.class);

                    CustomUserDetails customUserDetails = new CustomUserDetails(email, null, userId, orgMemberId, List.of());
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e) {

            ApiResponseDto apiResponseDto = new ApiResponseDto("Invalid JWT token", ResponseStatus.FAILURE);

            String jsonString = new ObjectMapper().writeValueAsString(apiResponseDto);

            response.getWriter().write(jsonString);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
