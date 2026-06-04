package com.alberto.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtTokenService {

    private final JWTVerifier jwtVerifier;

    public JwtTokenService(JWTVerifier jwtVerifier) {
        this.jwtVerifier = jwtVerifier;
    }

    public boolean isValid(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    public Instant getExpiresAt(String token) {
        if (token == null || token.isBlank()) {
            return null;
        }
        try {
            var jwt = jwtVerifier.verify(token);
            var expiresAt = jwt.getExpiresAt();
            return expiresAt == null ? null : expiresAt.toInstant();
        } catch (JWTVerificationException ex) {
            return null;
        }
    }
}
