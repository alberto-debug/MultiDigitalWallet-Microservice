package com.alberto.multidigitalwallet.user_service.security;

import com.alberto.multidigitalwallet.user_service.exception.TokenGenerationException;
import com.alberto.multidigitalwallet.user_service.exception.TokenValidationException;
import com.alberto.multidigitalwallet.user_service.model.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    @Value("${KEY}")
    private String secret;

    @Value("${JWT_EXPIRATION_MS:8640000}")
    private Long expirationMS;

    @Value("${JWT_ISSUER}")
    private String issuer;

    public String generateToken(User user){

        try{

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getEmail())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            log.error("Token Creation Failed", e);
            throw new TokenGenerationException("Failed to generate token ", e);
        }

    }

    public String validateToken( String token){
        try{

            if (token == null || token.isEmpty()){
                throw new TokenValidationException("Token cannot be null or empty");
            }

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();



        } catch (JWTCreationException e) {
            log.warn("JWT verification failed: {}", e.getMessage());
            throw new TokenValidationException("Invalid or expired token", e);
        } catch (Exception e) {
            log.error("Unexpected error during token validation", e);
            throw new TokenValidationException("Token validation failed", e);
        }

    }

    public Date expirationDate(){
        return new Date(System.currentTimeMillis()+expirationMS);
    }
}
