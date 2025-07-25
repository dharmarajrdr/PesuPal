package com.pesupal.server.security;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.exceptions.PermissionDeniedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY_STRING = "ZSM6vDEdLoH7zWvBRkjjQQ2pyuP6ZgOU";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
    private static final int JWT_EXPIRATION_IN_MILLISECONDS = StaticConfig.JWT_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

    public String generateToken(CustomUserDetails userDetails) {
        JwtBuilder builder = Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("user-id", userDetails.getUserPublicId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MILLISECONDS))
                .signWith(secretKey, Jwts.SIG.HS256);

        if (userDetails.getOrgMemberPublicId() != null) {
            builder.claim("org-member-id", userDetails.getOrgMemberPublicId());
        }

        return builder.compact();
    }

    public String generateToken(String subject, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofHours(24)); // or whatever

        return Jwts.builder()
                .subject(subject)
                .claims(extraClaims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(secretKey)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername());
    }

    public Claims extractAllClaims(String token) {
        try {
            JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            throw new PermissionDeniedException("Invalid or expired token.");
        }
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }
}