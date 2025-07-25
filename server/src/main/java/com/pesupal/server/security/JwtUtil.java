package com.pesupal.server.security;

import com.pesupal.server.config.StaticConfig;
import com.pesupal.server.exceptions.PermissionDeniedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY_STRING = "ZSM6vDEdLoH7zWvBRkjjQQ2pyuP6ZgOU"; // Generated in https://acte.ltd/utils/randomkeygen

    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    private static final int JWT_EXPIRATION_IN_MILLISECONDS = StaticConfig.JWT_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails
     * @return String - JWT token
     */
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MILLISECONDS))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

    }

    /**
     * Validates the JWT token against the provided user details.
     *
     * @param token
     * @param userDetails
     */
    public Boolean validateToken(String token, UserDetails userDetails) {

        String email = extractEmail(token);
        return email.equals(userDetails.getUsername());
    }

    /**
     * Extracts the email from the JWT token.
     *
     * @param token
     * @return String - Email extracted from the token
     */
    public String extractEmail(String token) {

        try {
            JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();
            return claims.getSubject(); // Subject is the email in this case
        } catch (Exception e) {
            throw new PermissionDeniedException("Invalid or expired token");
        }
    }
}
