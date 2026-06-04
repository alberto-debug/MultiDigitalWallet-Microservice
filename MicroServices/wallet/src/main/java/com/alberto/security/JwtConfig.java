package com.alberto.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public Algorithm jwtAlgorithm(@Value("${KEY}") String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET must be set");
        }
        return Algorithm.HMAC256(secret);
    }

    @Bean
    public JWTVerifier jwtVerifier(Algorithm algorithm, @Value("${JWT_ISSUER}") String issuer) {
        if (issuer == null || issuer.isBlank()) {
            throw new IllegalStateException("JWT_ISSUER must be set");
        }
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }
}
