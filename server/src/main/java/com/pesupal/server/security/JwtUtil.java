package com.pesupal.server.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY_STRING = "ZSM6vDEdLoH7zWvBRkjjQQ2pyuP6ZgOU"; // Generated in https://acte.ltd/utils/randomkeygen

    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    private int hourToMilliseconds(int hours) {

        return hours * 60 * 60 * 1000;
    }

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + hourToMilliseconds(1)))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();

    }
}
