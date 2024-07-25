package com.gabrielapredut.teachpoint.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtility {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey SECRET_KEY;

    @PostConstruct
    private void init() {
        if (secretKey == null || secretKey.length() < 32) {
            throw new IllegalArgumentException("Invalid JWT secret key length: " + secretKey.length());
        }
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            // Handle exceptions, e.g., token is invalid or expired
            return null;
        }
    }

    public boolean validateToken(String token, String username) {
        final String tokenUsername = getUsername(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            // Handle exceptions, e.g., token is invalid or malformed
            return true;
        }
    }
}

